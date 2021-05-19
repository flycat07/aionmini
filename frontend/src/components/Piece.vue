<template>
  <v-card>
    <v-card-title>조각 노트
      <v-btn class="ml-4" small @click="clear" dark>전체 초기화</v-btn>
    </v-card-title>
    <v-card-text>
      <v-simple-table>
        <colgroup>
          <col style="width:5%">
          <col>
          <col>
        </colgroup>
        <thead>
          <th></th>
          <th>조각 개수</th>
          <th>요새 이름</th>
          <th>초기화</th>
        </thead>
        <tbody>
          <tr v-for="(idx, id) in pieces" :key="id">
            <td><v-btn :disabled="pieces[id]===0" small  @click="plus(id)" color="primary">구매</v-btn></td>
            <td class="text-center" :class="{'red--text': pieces[id]===0, 'amber--text': pieces[id] === 1}">{{pieces[id]}} / 2</td>
            <td class="text-center">{{getTitle(id)}} 요새</td>
            <td class="text-center "><v-btn small @click="pieces[id] = 2" color="pink" dark>초기화</v-btn></td>
          </tr>
        </tbody>
      </v-simple-table>

    </v-card-text>
  </v-card>
</template>
<script>
export default {
  watch:{
    pieces: {
      deep: true,
      handler(){
        localStorage.setItem("HELPER_PIECE", JSON.stringify(this.pieces));
      }
    }
  },
  mounted() {
    const data = localStorage.getItem("HELPER_PIECE");
    if(data){
      this.pieces = JSON.parse(data);
    }
  },
  data(){
    return {
      pieces: {
        asteria: 2,
        roo: 2,
        crotan: 2,
        dkisas:2,
        rami: 2,
        tree: 2,
        west: 2,
        east: 2,
      }
    }
  },
  methods: {
    plus(key){
      if(this.pieces[key] > 0){
        this.pieces[key] -= 1;
      }

    },
    clear(){
      this.pieces =  {
        asteria: 2,
            roo: 2,
            crotan: 2,
            dkisas:2,
            rami: 2,
            tree: 2,
            west: 2,
            east: 2,
      }
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