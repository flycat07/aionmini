<template>
  <v-card>
    <v-card-title>아이템 링크 생성기</v-card-title>
    <v-card-subtitle>버튼을 클릭하고, 인게임에서 붙여넣기 하면 아이템링크가 생성됩니다.</v-card-subtitle>
    <v-card-text>

      <v-row>
        <v-col cols="12">
          <v-text-field v-model="keyword"
                        prepend-inner-icon="mdi-magnify"
                        outlined
                        label="검색"
                        flat/>
        </v-col>
        <v-divider />
        <v-col cols="12" class="items">
          <v-btn left @click="copyCode(s)" block outlined class="text-left mb-1" text v-for="s in suggest" :key="s.id">
            <v-icon left>mdi-content-copy</v-icon>
            <span v-html="addTagname(s.name)"></span>
          </v-btn>
        </v-col>
      </v-row>
    </v-card-text>
    <v-snackbar
        top
        text
        color="success"
        v-model="snackbar"
        elevation="5"
    >
      선택한 아이템이 복사 되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn
            text
            v-bind="attrs"
            icon
            @click="snackbar = false"

        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </template>

    </v-snackbar>
  </v-card>
</template>
<script>
import copy from 'copy-text-to-clipboard';

export default {
  mounted() {
    const str = localStorage.getItem("item");
    this.char = JSON.parse(str);
    console.info(str)
  },
  watch: {
    keyword () {
      this.search();
    },
    selectedChar () {
      // this.findChar();
    },
  },
  data () {
    return {
      snackbar: false,
      server: "",
      keyword: "",
      suggest: [],
      hour: 0,
      time: "",
      cancelSource: null,
      suggestLoading: false,
      selectedChar: {},
      showServerError: true,
      char: null,
      result: {},
    }
  },
  methods: {
    async search() {
      this.suggestLoading = true;
      this.cancelSource = this.$cencelToken.source();
      const params = new URLSearchParams();
      params.append('keyword', this.keyword);
      await this.axios.post(`/api/items`, params, {
        cancelToken: this.cancelSource.token }).then((response) => {
        if (response) {
          if(response.data.length > 0){
            this.suggest = response.data;
            // this.infoText = null;
            this.cancelSource = null;
          }
        }
      }).catch();
      this.suggestLoading = false;
    },
    addTagname(text){
      const keyword = this.keyword.split(" ").join("|");
      return text.replace(new RegExp("("+keyword+")", "g"), "<strong>$1</strong>")
    },
    copyCode({id}){
      copy(`[item:${id}]`);
      this.snackbar = true;
    },
    async findChar() {
      this.showServerError = false;
      const {server, userid} = this.selectedChar;
      if (server === 'GUARDIAN' || server === 'ARKAN') {
        this.showServerError = true;
        return;
      }
      const response = await this.axios.get(`/api/character/${server}/${userid}`);
      this.char = response.data;
      // this.suggest = response.data;
    },
  }

}

</script>
<style>
strong{
  color: #dd2c00;
}
.items .v-btn__content{
  display: block !important;
}
</style>