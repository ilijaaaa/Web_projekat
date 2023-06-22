<template>
    <div>
      <h1>Zahtevi</h1>
      <div v-if="zahtevi.length === 0">
        <p>Nema zahteva</p>
      </div>
      <div v-else>
        <ul>
          <li v-for="zahtev in zahtevi" :key="zahtev.id">
            <p>Mejl: {{ zahtev.mejl }}</p>
            <p>Telefon: {{ zahtev.telefon }}</p>
            <p>Poruka: {{ zahtev.poruka }}</p>
            <p>Datum: {{ zahtev.datum }}</p>
            <p>Status: {{ zahtev.status }}</p>
            <p>Autor: {{ zahtev.autor }}</p>
            <button @click="prihvati">Prihvati</button>
            <button @click="odbij">Odbij</button>
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        zahtevi: [],
      };
    },
    mounted() {
      this.fetchZahtevi();
    },
    methods: {
      fetchZahtevi() {
        axios.get('http://localhost:8080/api/zahtevi/' + localStorage.getItem('korisnik'))
          .then((response) => {
            this.zahtevi = response.data;
          })
          .catch((error) => {
            console.error('Error:', error);
          });
      },
      prihvati(){

      },
      odbij(){

      },
    },
  };
  </script>
  