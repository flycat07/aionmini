<template>
  <v-card width="600">
    <v-card-title>링크 생성기</v-card-title>
    <v-card-text>
      <v-row>
        <v-col cols="8">
          <v-autocomplete
              outlined
              flat
              hide-details
              label="검색"
              prepend-inner-icon="mdi-magnify"
              no-data-text="데이터가 없습니다."
              item-text="charname"
              v-model="selectedChar"
              :items="suggest"
              :search-input.sync="keyword"
              return-object
          ></v-autocomplete>
        </v-col>
        <v-col cols="4">
          <v-select flat outlined :items="servers" item-text="name" item-value="type" label="서버">
          </v-select>
        </v-col>

      </v-row>

      <v-alert v-if="showServerError">가디언, 아칸서버는 지원되지 않습니다.</v-alert>

      {{char}}

    </v-card-text>
  </v-card>
</template>
<script>
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
      selectedChar: {},
      showServerError: true,
      char: null,
      result: {},
      servers: [
        {id: 1, name: "가디언", type: "GUARDIAN"},
        {id: 2, name: "아칸", type: "ARKAN"},
        {id: 21, name: "이스라펠", type: "ISRAFEL"},
        {id: 22, name: "네자칸", type: "NEZAKAN"},
        {id: 23, name: "지켈", type: "ZICKEL"},
        {id: 24, name: "바이젤", type: "BYZEL"},
        {id: 25, name: "트리니엘", type: "TRINIEL"},
        {id: 26, name: "카이시넬", type: "KAISINEL"},
        {id: 27, name: "루미엘", type: "LUMIEL"},
      ]
    }
  },
  methods: {
    async search() {
      this.showServerError = false;
      const response = await this.axios.get(`/api/suggest?keyword=${this.keyword || ''}&server=${this.server}`);
      this.suggest = response.data;
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