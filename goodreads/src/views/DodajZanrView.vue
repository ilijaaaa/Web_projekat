<template>
    <div>
      <h2>Dodaj žanr</h2>
      <form @submit.prevent="dodajZanr" class="form">
        <div class="form-group">
            <input v-model="stringDto" type="text" placeholder="Žanr" required/>
        </div>
        <button type="submit" class="submit-button">Dodaj</button>
      </form>
      <p v-if="error" class="error">{{ error }}</p>
    </div>
</template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        stringDto: '',
        error: ''
      };
    },
    methods: {
      dodajZanr() {
        axios.post("http://localhost:8080/api/zanr", { value: this.stringDto }, {params: {sessionId: localStorage.getItem('korisnik')}})
            .then(response => {
                console.log(response.data);
                this.$router.go(-1);
            })
            .catch(error => {
                console.error(error);
                this.error = error.response.data;
        });
      }
    }
  };
  </script>
  
  <style>

.form-group {
    margin-bottom: 20px;
}

input[type="text"] {
    width: 25%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.submit-button {
    width: 7%;
    padding: 10px;
    font-size: 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-button:hover {
    background-color: #0056b3;
}

  .error {
    color: red;
  }
  
  </style>
  