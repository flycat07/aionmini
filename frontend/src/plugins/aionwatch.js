"use strict";
// Object.defineProperty(exports, "__esModule", { value: true });
let AionWatch = class {

    constructor() {
        const date = new Date();
        date.setDate(date.getDate() - 5);
        this._time = date.getTime() ;
    }

    getTime () {
        return (this._time + 1000 * 60 * 60 ) * 12 - 30000;
    }
    getYear () {
        return Math.floor((this.getTime() / (1000 * 60 * 60 * 24 * 31 * 12))) - 365;
    }
    getMonth () {
        return Math.floor((this.getTime() / (1000 * 60 * 60 * 24 * 31) % 12));
    }
    getDate () {
        return Math.floor((this.getTime() / (1000 * 60 * 60 * 24) % 31));
    }
    getHour () {
        return Math.floor((this.getTime() / (1000 * 60 * 60)) % 24);
    }
    getMinutes () {
        return Math.floor((this.getTime() / (1000 * 60) - (4))  % 60);
    }

}

export default AionWatch;
