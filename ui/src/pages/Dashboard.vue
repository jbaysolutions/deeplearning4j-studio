<template>
  <div>
    <h1>DeepLearning4J Studio</h1>
    <p class="lead"></p>

    <ClosableCard
      title="DL4J - Deep Learning for Java"
      message="Deeplearning4j - Open-source, distributed, deep learning library for the JVM</p><p>DeepLearning4J Studio makes use of the DL4J libraries. To learn more about how Neural Networks are built, trained and used, you should visit the DL4J website and check the documentation."
      buttonUrl="https://deeplearning4j.org/"
      buttonText="deeplearning4j.org"
      />

  </div>
</template>
<script>
  import assets from '../assets'
  import ClosableCard from '../components/common/ClosableCard'

  export default {
    components: {
      ClosableCard,
    },
    computed: {
      logos() {
        return assets
      }
    }
  }
</script>
