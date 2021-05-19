<template>
  <div>
  <v-card>
    <v-card-title>조각 노트
      <v-spacer />
      <v-btn class="ml-4" small @click="copyAll('a')" outlined ><v-icon left>mdi-content-copy</v-icon>조각위치 전체복사(천족)</v-btn>
      <v-btn class="ml-4" small @click="copyAll('d')" outlined ><v-icon left>mdi-content-copy</v-icon>조각위치 전체복사(마족)</v-btn>
      <v-btn class="ml-4" small @click="clear" color="pink" dark>전체 초기화</v-btn>
    </v-card-title>
    <v-card-text>
      <v-simple-table>
        <colgroup>
          <col style="width:10%">
          <col style="width:10%">

          <col>
          <col style="width:30%">
        </colgroup>
        <thead>
          <th></th>
          <th>조각 개수</th>
          <th>요새 이름</th>
          <th>기능</th>
        </thead>
        <tbody>
          <tr v-for="(idx, id) in pieces" :key="id">
            <td><v-btn :disabled="pieces[id].count===0" small  @click="plus(id)" color="primary">구매</v-btn></td>
            <td class="text-center" :class="{'red--text': pieces[id].count===0, 'amber--text': pieces[id].count === 1}">{{pieces[id].count}} / 2</td>
            <td class="text-center">{{getTitle(id)}} 요새</td>
            <td class="text-center ">
              <v-btn small class="mr-2" outlined @click="copyPosition(pieces[id].pos_a)"><v-icon left>mdi-content-copy</v-icon>천족 위치</v-btn>
              <v-btn small class="mr-2" outlined @click="copyPosition(pieces[id].pos_a)"><v-icon left>mdi-content-copy</v-icon>마족 위치</v-btn>
              <v-btn small @click="pieces[id].count = 2" color="pink" dark>초기화</v-btn>
            </td>
          </tr>
        </tbody>
      </v-simple-table>

    </v-card-text>
  </v-card>
    <v-snackbar
        top
        text
        color="success"
        v-model="snackbar"
        elevation="5"
    >
      선택한 위치가 복사되었습니다.
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
  </div>
</template>
<script>
import copy from 'copy-text-to-clipboard';
export default {
  watch:{
    pieces: {
      deep: true,
      handler(){
        localStorage.setItem("HELPER_PIECES", JSON.stringify(this.pieces));
      }
    }
  },
  mounted() {
    const data = localStorage.getItem("HELPER_PIECES");
    if(data){
      const history = JSON.parse(data);
      Object.keys(history).forEach(key => this.pieces[key].count = history[key].count);
      // this.pieces = JSON.parse(data);
    }
  },
  data(){
    return {
      snackbar: false,
      pieces: {
        asteria: {
          title: '아스테리아',
          count: 2,
          pos_a: '[pos:아스테리아;400010000 426.7 3059.2 0.0 3]',
          pos_d: '[pos:아스테리아;400010000 457.4 2747.1 0.0 3]'
        },
        roo: {
          title: '고대도시 루',
          count: 2,
          pos_a: '[pos:고대도시 루;400010000 3280.2 914.2 0.0 3]',
          pos_d: '[pos:고대도시 루;400010000 3097.9 1496.8 0.0 3]'
        },
        crotan: {
          title: '크로탄',
          count: 2,
          pos_a: '[pos:크로탄;400010000 1732.5 1416.6 0.0 3]',
          pos_d: '[pos:크로탄;400010000 1839.3 1573.1 0.0 3]'
        },
        dkisas:{
          title: '드키사스',
          count: 2,
          pos_a: '[pos:드키사스;400010000 2475.3 2393.0 0.0 3]',
          pos_d: '[pos:드키사스;400010000 2769.7 2018.1 0.0 3]'
        },
        rami: {
          title: '라미렌',
          count: 2,
          pos_a: '[pos:라미렌;400010000 1709.5 2072.1 0.0 3]',
          pos_d: '[pos:라미렌;400010000 1522.1 2369.5 0.0 3]'
        },
        tree: {
          title: '유황나무',
          count: 2,
          pos_a: '[pos:유황나무;400010000 1411.9 880.1 0.0 1]',
          pos_d: '[pos:유황나무;400010000 979.5 1359.4 0.0 1]'
        },
        west: {
          title: '시엘의 서부',
          count: 2,
          pos_a: '[pos:시엘의 서부;400010000 3308.2 2740.2 0.0 1]',
          pos_d: '[pos:시엘의 서부;400010000 3277.7 3016.0 0.0 1]'
        },
        east: {
          title: '시엘의 동부',
          count: 2,
          pos_a: '[pos:시엘의 동부;400010000 3133.1 3156.1 0.0 1]',
          pos_d: '[pos:시엘의 동부;400010000 2919.8 3309.1 0.0 1]'
        },
      }
    }
  },
  methods: {
    copyPosition(str){
      copy(str);
      this.snackbar = true;
    },
    plus(key){
      if(this.pieces[key].count > 0){
        this.pieces[key].count -= 1;
      }

    },
    copyAll(race){
      const maps = Object.keys(this.pieces).map(p => this.pieces[p]['pos_'+race]);
      maps.length = 5;
      copy((race === 'a' ? '천족 : ' : '마족 : ')+ maps.join(", "));
    },
    clear(){
      Object.keys(this.pieces).forEach(p => this.pieces[p].count = 2);
    },
    getTitle(type){
      switch (type) {
        case 'asteria': return "아스테리아"
        case 'roo': return "고대도시 루"
        case 'crotan': return "크로탄"
        case 'dkisas': return "드키사스"
        case 'rami': return "라 미렌"
        case 'tree': return "유황나무"
        case 'west': return "시엘의 서부"
        case 'east': return "시엘의 동부"
      }
      return "";
    }
  }
}
</script>