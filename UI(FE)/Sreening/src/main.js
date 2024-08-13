// main.js hoặc app.js
import { createApp } from 'vue';
import App from './App.vue';

// Định nghĩa filter
const app = createApp(App);

app.config.globalProperties.$filters = {
  formatDate(date) {
    const d = new Date(date);
    const day = ('0' + d.getDate()).slice(-2);
    const month = ('0' + (d.getMonth() + 1)).slice(-2);
    const year = d.getFullYear();
    return `${day}-${month}-${year}`;
  }
};

app.mount('#app');
