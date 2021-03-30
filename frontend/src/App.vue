<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-btn text dark @click="openAion">
        <v-img
          alt="Aion"
          class="shrink mr-2"
          contain
          src="https://imgfiles-cdn.plaync.com/file/contents/download/20200619064945-tnwXZgDbd0m6GYurJcxV0-v4"
          width="80"
        />
        </v-btn>
        <span>아이온 헬퍼</span>
      </div>
      <v-spacer></v-spacer>

      <v-tooltip left>
        <template v-slot:activator="{ on, attrs }">
          <v-badge icon="mdi-crown" overlap>
          <v-btn @click="openGuestbook" icon class="mx-0" v-bind="attrs" v-on="on">
            <v-avatar>
              <v-img src="https://profileimg.plaync.com/game_profile_images/aion/images?gameServerKey=22&charKey=72701"></v-img>
            </v-avatar>
          </v-btn>
          </v-badge>
        </template>
        <span>제작자</span>
      </v-tooltip>

    </v-app-bar>
    <v-main  class="grey lighten-3">
      <v-container >
        <v-row>
          <v-col cols="12" v-if="navi === 'findchar'"><find-char></find-char></v-col>
          <v-col cols="12" v-if="navi === 'link'"><link-gen></link-gen></v-col>
        </v-row>
      </v-container>
    </v-main>

    <v-bottom-navigation v-model="navi">
      <v-btn value="findchar">
        <span>캐릭터 검색</span>
        <v-icon>mdi-human-greeting</v-icon>
      </v-btn>

      <v-btn value="link">
        <span>아이템 링크</span>
        <v-icon>mdi-link</v-icon>
      </v-btn>

<!--      <v-btn value="nearby">-->
<!--        <span>레기온 문장 생성기</span>-->
<!--        <v-icon>mdi-flag-checkered</v-icon>-->
<!--      </v-btn>-->
    </v-bottom-navigation>
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
    // setInterval(this.updateTime, 300)
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
      navi: 1,
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
    openAion(){
      window.open(`https://aion.plaync.com/`);
    },
    openGuestbook(){
      window.open(`https://aion.plaync.com/characters/server/22/id/72701/board/guestbook`);
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
