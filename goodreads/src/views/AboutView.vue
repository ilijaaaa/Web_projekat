<template>
  <div class="about">
    <h1>Goodreads je najveći svetski sajt za čitaoce i preporuke za knjige. Naša misija je da pomognemo ljudima da pronađu
      i podele knjige koje vole. Goodreads je pokrenut u januaru 2007. </h1>
  </div>
  <img :src="require('../assets/about.jpg')" />
</template>

<script>
export default {
}
</script>

<style>
.about {
  padding: 20px;
  background-color: #f7f7f7;
  text-align: center;
}

h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

img {
  height: 750px;
}
</style>