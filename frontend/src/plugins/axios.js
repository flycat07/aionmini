"use strict";

import Vue from 'vue';
import axios from "axios";

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = 'http://reikop.com:8080';

// const cancelTokenSource = this.axios.CancelToken.source();

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || "",
  baseURL: process.env.NODE_ENV === 'production' ? 'http://reikop.com:8080' : '/',
  origin: 'http://reikop.com:8080',
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);
const $cencelToken = axios.CancelToken;
// const CancelToken = axios.CancelToken;

_axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  function(response) {
    // Do something with response data
    return response;
  },
  function(error) {
    // Do something with response error
    return Promise.reject(error);
  }
);

Plugin.install = function(Vue) {
  Vue.prototype.$cencelToken = $cencelToken;
  // Vue.prototype.$axios = $axios;
  // Vue.axios = _axios;
  // window.cancelToken = CancelToken;
  // window.axios = _axios;
  
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;
