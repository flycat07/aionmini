<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Aion"
          class="shrink mr-2"
          contain
          src="https://imgfiles-cdn.plaync.com/file/contents/download/20200619064945-tnwXZgDbd0m6GYurJcxV0-v4"
          width="80"
        />
        <span>아이온 헬퍼</span>
      </div>
      <v-spacer></v-spacer>
      <span><v-icon left color="accent" v-text="hour > 6 || hour < 18 ? 'mdi-weather-sunny' : 'mdi-moon-waning-crescent'"></v-icon>{{time}}</span>
    </v-app-bar>
    <v-main>
      <v-container>
        <v-row>
          <v-col cols="6"><find-char></find-char></v-col>
          <v-col cols="6"><link-gen></link-gen></v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import AionWatch from "./plugins/aionwatch"
import FindChar from "./components/FindChar"
import LinkGen from "./components/LinkGen"
export default {
  name: 'App',

  components: {
    FindChar,
    LinkGen
  },
  //
  // data: () => ({
  //   //
  // }),
  mounted() {
    setInterval(this.updateTime, 300)
  },
  watch: {
    keyword () {
      this.search();
    },
    selectedChar () {
      this.findChar();
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
      char: {},
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
  methods:{
    async search(){
      const response = await this.axios.get(`/api/suggest?keyword=${this.keyword || ''}&server=${this.server}`);
      this.suggest = response.data;
    },
    async findChar(){
      const {server, userid} = this.selectedChar;
      if(server === 'GUARDIAN' || server === 'ARKAN'){
        return;
      }
      const response = await this.axios.get(`/api/character/${server}/${userid}`);
      this.char = response.data;
      console.info(this.char)
      // this.suggest = response.data;
    },
    updateTime(){
      const watch = new AionWatch();
      this.hour = watch.getHour();
      const hour = String(watch.getHour()).padStart(2,'0');
      const min = String(watch.getMinutes()).padStart(2,'0');
      this.time = `${watch.getYear()}년 ${watch.getMonth()}월 ${watch.getDate()}일 ${hour}:${min}`;
    }
  }
};
</script>
