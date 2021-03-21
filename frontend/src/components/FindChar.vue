<template>
  <v-card>
    <v-card-title>캐릭터 검색기</v-card-title>
    <v-card-text>
      <v-row>
        <v-col cols="12">
          <v-btn class="mr-3" @click="clearHistory" x-small color="red" dark><v-icon left x-small>mdi-sticker-remove-outline</v-icon>전체 삭제</v-btn>
          <v-btn class="mr-1" @click="selectedChar = h" v-for="(h, index) in history" v-text="h.charname" :key="index" x-small outlined></v-btn>
        </v-col>
        <v-col cols="12">
          <v-checkbox v-model="only50" label="50미만 표시안함" hide-details class="ma-0"></v-checkbox>
        </v-col>
        <v-col cols="6" >
          <v-select v-model="selectedServer" flat outlined
                    clearable
                    :items="servers" item-text="name" item-value="type" label="서버">
          </v-select>
        </v-col>
        <v-col cols="6">
          <v-autocomplete
              ref="suggest"
              outlined
              flat
              label="검색"
              prepend-inner-icon="mdi-magnify"
              no-data-text="데이터가 없습니다."
              item-text="charname"
              item-value="userid"
              v-model="selectedChar"
              :items="suggest"
              :search-input.sync="keyword"
              return-object
              clearable
              auto-select-first
              :loading="suggestLoading"

          >
            <template v-slot:item="{ item }">

          <v-list-item-avatar>
           <img :src="'https://profileimg.plaync.com/game_profile_images/aion/images?gameServerKey='+getOriginServerId(item.server)+'&charKey='+item.userid" >
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>{{item.charname}}</v-list-item-title>
            <v-list-item-subtitle> {{item.serverName}} / Lv.{{item.level}} / {{item.raceName}} / {{item.className}}</v-list-item-subtitle>
          </v-list-item-content>

            </template>

          </v-autocomplete>
        </v-col>
      </v-row>


<v-divider class="my-1" v-if="selectedChar != null"></v-divider>
      <v-list-item-content class="justify-center" v-if="selectedChar != null && selectedChar.level">
        <div class="mx-auto text-center">
          <v-avatar tile class="mr-2" size="100" >
            <img :src="'https://profileimg.plaync.com/game_profile_images/aion/images?gameServerKey='+getOriginServerId(selectedChar.server)+'&charKey='+selectedChar.userid"
            :alt="selectedChar.charname"/>
            </v-avatar>
          <v-btn text outlined class="mr-2" @click="newWindow">
            <v-icon left>mdi-share</v-icon>

            #{{selectedChar.charname}}

            #LV.{{selectedChar.level}}
            #{{selectedChar.serverName}}
            #{{selectedChar.className}}
          </v-btn>
          <v-btn v-if="selectedChar.guildId" text outlined class="mr-2" @click="showLegion">
            #{{selectedChar.guildName}}
          </v-btn>
       
        </div>
      </v-list-item-content>
       <v-divider class="my-1" v-if="selectedChar != null"></v-divider>
      <v-row>
        <v-col cols="12" sm="6" md="12" lg="12" xl="6">
        <v-simple-table dense dark
                        class="pb-1"
                        style="background-color: #2e373e"
                        v-if="selectedChar != null">
        <thead>
        <tr><td colspan="2">주요 정보</td>
        </tr>
        </thead>
        <tbody>
          <tr v-if="findCharLoading">
            <td colspan="2" class="text-center pa-10">
              <v-progress-circular
                  :size="70"
                  :width="7"
                  indeterminate
                ></v-progress-circular>
            </td>
          </tr>
          <template>
        <tr v-if="!findCharLoading">
          <th class="text-left">생명력</th>
          <td class="text-right">{{totalStat.hp | fmt}}</td>
        </tr>
        <tr>
          <th class="text-left">마법저항</th>
          <td class="text-right" :class="{'red--text':totalStat.magicResist > 1700}">{{totalStat.magicResist | fmt}}</td>
        </tr>
        <tr v-if="totalStat.block > 1000 && (this.selectedChar.className ==='치유성' || this.selectedChar.className ==='수호성' || this.selectedChar.className ==='호법성')">
          <th class="text-left">방패방어</th>
          <td class="text-right" :class="{'red--text':totalStat.block > 2650}">{{totalStat.block | fmt}}</td>
        </tr>
        <tr v-if="classType()==='P'">
          <th class="text-left">공격력</th>
          <td class="text-right">{{Math.max(totalStat.physicalLeft, totalStat.physicalRight) | fmt}}</td>
        </tr>
        <tr v-if="classType()==='P'">
          <th class="text-left">명중</th>
          <td class="text-right">{{Math.max(totalStat.accuracyLeft, totalStat.accuracyRight) | fmt}}</td>
        </tr>
        <tr v-if="classType()==='P'">
          <th class="text-left">물리치명타</th>
          <td class="text-right">{{Math.max(totalStat.criticalLeft, totalStat.criticalRight) | fmt}}</td>
        </tr>
        <tr v-if="classType()==='M'">
          <th class="text-left">마법증폭</th>
          <td class="text-right" :class="{'red--text':totalStat.magicalBoost > 2400}">{{totalStat.magicalBoost | fmt}}</td>
        </tr>

        <tr v-if="classType()==='M'">
          <th class="text-left">마법적중</th>
          <td class="text-right" :class="{'red--text':totalStat.magicalAccuracy > 1700}">{{totalStat.magicalAccuracy | fmt}}</td>
        </tr>
            <tr>
              <th class="text-left">물치저항</th>
              <td class="text-right">{{totalStat.phyCriticalReduceRate | fmt}}</td>
            </tr>
            <tr>
              <th class="text-left">물치방어</th>
              <td class="text-right">{{totalStat.phyCriticalDamageReduce | fmt}}</td>
            </tr>
        <tr>
          <th class="text-left">PVP 공격력</th>
          <td class="text-right">{{totalAbyss.att | fix}}%</td>
        </tr>
        <tr>
          <th class="text-left">PVP 방어력</th>
          <td class="text-right">{{totalAbyss.def | fix}}%</td>
        </tr>

            <tr v-if="!findCharLoading">
              <th class="text-left">어비스 포인트</th>
              <td class="text-right">{{char.character_abyss.abyssPoint | fmt}}</td>
            </tr>
        <tr v-if="!findCharLoading">
          <th class="text-left">어비스 계급</th>
          <td class="text-right">{{char.character_abyss.rankName}}</td>
        </tr>

            <tr>
              <th class="text-left">킬 수</th>
              <td class="text-right" :class="{'red--text': (char.character_abyss || {}).totalKillCount > 10000}">{{ (char.character_abyss || {}).totalKillCount | fmt}}</td>
            </tr>
        </template>
        </tbody>
      </v-simple-table>
        </v-col>


<!--      <v-divider  v-if="selectedChar != null"/>-->
      <v-col cols="12" sm="6" md="12" lg="12" xl="6">
      <v-simple-table dense  v-if="selectedChar != null"
                      class="pb-2"
                      style="background-color: #564b37" dark>
        <template v-slot:default>
          <thead>
          <tr><td colspan="2">장착 아이템</td>
          </tr>
          </thead>
          <tbody >
            <tr v-if="findCharLoading">
              <td min-height="400px" class="text-center pa-10">
              <v-progress-circular
                  :size="70"
                  :width="7"
                  indeterminate
                ></v-progress-circular>
              </td>
            </tr>
            <template  v-if="!findCharLoading">
          <tr v-for="index in equipSort" :key="index">
            <td>
              <template v-if="equipSlot[index] != null" >
                <v-avatar style="cursor: pointer" size="30px" tile @click="openInven(equipSlot[index][0].itemId)">
                  <img :src="equipSlot[index][0].image">
                </v-avatar>
                <span class="ml-2" :style="{color: getColor(equipSlot[index][0].quality)}">
                  <template v-if="equipSlot[index][0].enchantCount > 0">+{{equipSlot[index][0].enchantCount}} </template>{{equipSlot[index][0].name}}
                </span>
              </template>
            </td>
          
          </tr>
            </template>

          </tbody>
        </template>
      </v-simple-table>
      <v-divider  v-if="selectedChar != null"/>
      <v-simple-table style="background-color: #181a26"
                      dark
                      class="mt-5 pb-1" dense  v-if="selectedChar != null">
        <template v-slot:default>
          <thead>
          <tr><td colspan="2">장착 스티그마</td>
          </tr>
          </thead>
          <tbody>
          <tr v-if="findCharLoading">
            <td min-height="400px" class="text-center pa-10">
              <v-progress-circular
                  :size="70"
                  :width="7"
                  indeterminate
              ></v-progress-circular>
            </td>
          </tr>
          <template  v-if="!findCharLoading">
            <tr v-for="(sti, index) in char.character_stigma" :key="index">
              <td>
                  <v-avatar style="cursor: pointer" size="30px" tile
                            @click="openInven(sti.itemId)">
                    <img :src="sti.image">
                  </v-avatar>
                  <span class="ml-2" :style="{color: getColor(sti.quality)}">{{sti.name}}</span>

              </td>

            </tr>
          </template>

          </tbody>
        </template>
      </v-simple-table>
      </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>
<script>
import _ from 'lodash';

export default {
  name:"findChar",
  components:{
  },
  mounted() {
    const server = localStorage.getItem("server");
    this.only50 = localStorage.getItem("only50") === 'true';
    if(server){
      this.selectedServer = server;
    }
        


    // const str = localStorage.getItem("item");
    // this.char = JSON.parse(str);
    // if(this.char.character_equipments){
    //   this.char.character_equipments = _.sortBy(this.char.character_equipments, 'equipSlotType');
    // }
    this.loadHistory();
  },
  computed: {
    totalStat(){
      return (this.char.character_stats || {} ).totalStat || {};
    },
    totalAbyss(){
      return this.calcAbyss(this.char.character_equipments || [])
    },
    equipSlot(){
      return _.groupBy(this.char.character_equipments, 'equipSlotType');
    },
    abyssItemCount(){
      if(!this.char.character_equipments){
        return 0;
      }
      return this.char.character_equipments.filter(n => /(가디언|아칸)\s.부장/.test(n.name)).length;
    }
  },
  watch: {
    only50(){
      if(this.only50){
        localStorage.setItem("only50", 'true');
      }else{
        localStorage.removeItem("only50");
      }
    },
    keyword(){
      if(this.keyword != null && this.keyword !== ""){
        this.search((this.keyword || "").replaceAll(/[^a-zA-Zㄱ-힣]/gi, ""));
      }
    },

    selectedServer(){
      localStorage.setItem("server", this.selectedServer);
    },

    selectedChar () {
      if(this.selectedChar && this.selectedChar.userid){
        this.addThisChar();
        this.findChar();
        this.loadHistory();
      }
    },
  },
  data () {
    return {
      chartData: { // for 'data' prop of 'bar-chart'
        categories: ['Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov'],
        series: [
          {
            name: 'Budget',
            data: [5000, 3000, 5000, 7000, 6000, 4000],
          },
          {
            name: 'Income',
            data: [8000, 4000, 7000, 2000, 6000, 3000],
          },
          {
            name: 'Expenses',
            data: [4000, 4000, 6000, 3000, 4000, 5000],
          },
          {
            name: 'Debt',
            data: [3000, 4000, 3000, 1000, 2000, 4000],
          },
        ],
      },
      equipSort: [0,17,1,18,3,11,12,4,5,2,10,6,7,8,9,16,15],
      selectedServer: null,
      only50: false,
      findCharLoading: false,
      server: "",
      keyword: "",
      cancelSource: null,
      suggest: [],
      hour: 0,
      time: "",
      selectedChar: null,
      suggestLoading: false,
      showServerError: false,
      char: {},
      result: {},
      history: [],
      abyssItem: {
        'TEN': { //십부
          ARMOR: { SHIELD: 4, HEAD: 1.6, SHOULDER: 2.4, FOOT: 2.4, TORSO:4, LEG: 3.2, HAND: 2.4 },
          ACCESSORY: { FINGER: 1.6, WAIST: 1.6, EAR: 2.4, NECK:3.2 },
          WEAPON: { BOTH: 8, RIGHT: 4.8 },
        },
        'HUN': { //백부
          ARMOR: { SHIELD: 4.5, HEAD: 1.8, SHOULDER: 2.7, FOOT: 2.7, TORSO:4.5, LEG: 3.6, HAND: 2.7 },
          ACCESSORY: { FINGER: 1.8, WAIST: 1.8, EAR: 2.7, NECK:3.6 },
          WEAPON: { BOTH: 9, RIGHT: 5.4 }
        },
        'THO': { //천부
          ARMOR: { SHIELD: 5, HEAD: 2, SHOULDER: 3, FOOT: 3, TORSO:5, LEG: 4, HAND: 3 },
          ACCESSORY: { FINGER: 2, WAIST: 2, EAR: 3, NECK:4 },
          WEAPON: { BOTH: 10, RIGHT: 6 }
        },
      },
      servers: [
        // {id: 1, name: "가디언", type: "GUARDIAN"},
        // {id: 2, name: "아칸", type: "ARKAN"},
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
    async search(keyword) {
      this.cancelSource && this.cancelSource.cancel();
      this.suggestLoading = true;
      this.showServerError = false;
      this.cancelSource = this.$cencelToken.source();
      const response = await this.axios.get(`/api/suggest?keyword=${keyword || ''}&server=${this.selectedServer || ''}`, {
        cancelToken: this.cancelSource.token
      });
      if (response && response.data) {
        this.suggest = response.data.filter(s => !this.only50 || s.level === 50);
      }else{
        this.suggest = [];
      }
      this.suggestLoading = false;
    },
    async findChar() {
      this.findCharLoading = true;
      this.showServerError = false;
      const {server, userid} = this.selectedChar;
      if (server === 'GUARDIAN' || server === 'ARKAN') {
        this.showServerError = true;
        return;
      }
      const response = await this.axios.get(`/api/character/${server}/${userid}`);
      this.char = response.data;
      this.findCharLoading = false;

      // console.info(this.char)
      // this.suggest = response.data;
    },
    newWindow(){
      const {server, userid} = this.selectedChar;
      const id = this.getOriginServerId(server);
      window.open(`https://aion.plaync.com/characters/server/${id}/id/${userid}/home`);
    },
    showLegion(){
      const {server, guildId} = this.selectedChar;
      const id = this.getOriginServerId(server);
      window.open(`https://aion.plaync.com/legions/server/${id}/id/${guildId}/home`);
    },

    getOriginServerId(name){
      return _.find(this.servers, n => n.type === name).id;
    },
    openInven(id){
      window.open(`http://aiondatabase.net/kr/item/${id}`);
    },
    classType(){
      switch (this.selectedChar.className) {
        case '마도성' :
        case '정령성' :
        case '치유성' :
          return 'M';
        case '검성' :
        case '살성' :
        case '궁성' :
        case '수호성' :
        case '호법성' :
          return 'P';
      }
      return 'P';
    },
    getColor(quality){
      switch (quality){
        case 'common' : return "#acacac";
        case 'rare' : return "#38d723"; //629c5b
        case 'legend' : return "#1bcdf3"; //5294ac
        case 'unique' : return "#e3c565"; //ada551
        case 'epic' : return "#e47b4c";  //b47150
        case 'mythic' : return "#9b4aff";
        case 'ancient' : return "#f1a12e";
        case 'relic' : return "#dd43ef";
        case 'finality' : return "#e14141";
        case 'junk' : return "#666";
      }
      return "#acacac";
    },
    addThisChar(){
      const history = JSON.parse(localStorage.getItem("history") || "[]");
      const {server, userid} = this.selectedChar;
      if(_.find(history, {server, userid}) == null){
        history.unshift(this.selectedChar);
        if(history.length > 10){
          history.length = 10;
        }
        localStorage.setItem("history",JSON.stringify(history));
      }
    },
    autoSelect(){
      // console.info(this.selectedChar.userid, this.keyword === this.suggest[0].charname)
      // if(!(this.selectedChar && this.selectedChar.userid)){
        if(this.keyword === this.suggest[0].charname){
          this.selectedChar = this.suggest[0];
          this.$refs.suggest.blur();
        }
      // }
    },
    clearHistory(){
      localStorage.removeItem("history");
      this.history = []
    },
    loadHistory(){
      this.history = JSON.parse(localStorage.getItem("history") || "[]");
    },
    calcAbyss(equips){
      let def = 0;
      let att = 0;
      for(const equip of equips){
        if( /(가디언|아칸)\s.부장/.test(equip.name)){
          const type = /(십|백|천|만)부/.exec(equip.name)[1];
          let level = 'TEN';
          switch(type){
            case '십' :  level = 'TEN'; break;
            case '백' :  level = 'HUN'; break;
            case '천' :  level = 'THO'; break;
          }
          const item = this.abyssItem[level];
          const category = [equip.category1.alias, equip.category2.alias, equip.category3.alias];
          if(category[0] === 'ACCESSORY'){
            att += item[category[0]][category[1]];
          }else if(category[0] === 'ARMOR'){
            if(category[1] === "HEAD"){
              def += item[category[0]][category[1]];
            }else if(category[1] === "SHIELD"){
              def += item[category[0]][category[1]];
            }else{
              def += item[category[0]][category[2]];
            }
          }else if(category[0] === 'WEAPON'){
            let weaponType = 'BOTH';
            switch (category[1]) {
              case 'ORB' :
              case 'BOOK' :
              case 'TWOHANDSWORD' :
              case 'STAFF' :
              case 'BOW' :
              case 'POLEARM' :
                weaponType = 'BOTH'; break;
              case 'MACE' :
              case 'SWORD' :
              case 'DAGGER' :
                weaponType = 'RIGHT'; break;
            }
            att += item[category[0]][weaponType];
          }
        }else if(equip.name === '라크하네의 머리장식'){
          def += 2;
        }
      }
      return {def, att}
    }
  }

}

</script>