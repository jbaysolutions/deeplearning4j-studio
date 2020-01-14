<template>
  <div>
    <h1>DeepLearning4J Studio</h1>
    <p class="lead">
      Use this document as a way to quickly start any new project.<br>
      All you get is this text and a mostly barebones HTML document.
    </p>

    <div class="card">
      <div class="card-header">
        Featured
      </div>
      <div class="card-body">
        <h5 class="card-title">Special title treatment</h5>
        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
        <a href="#" class="btn btn-primary">Go somewhere</a>
      </div>
    </div>
  </div>
</template>
<script>
  import assets from '../assets'

    export default {
      computed: {
        logos() {
          return assets
        }
      }
    }
</script>
