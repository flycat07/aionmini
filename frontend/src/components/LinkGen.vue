<template>
  <v-card>
    <v-card-title>아이템 링크 생성기</v-card-title>
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
      await this.axios.get(`/api/items?keyword=${this.keyword || ''}`, {
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
      console.info(keyword)
      return text.replace(new RegExp("("+keyword+")", "g"), "<strong>$1</strong>")
    },
    copyCode({id}){
      copy(`[item:${id}]`);
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
      console.info(this.char)
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