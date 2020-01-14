<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      {{title}}
      <button
              type="button"
              class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse" :id="widgetId" >
      <div class="card-body">
        <h5 class="card-title"></h5>
        <div class="card-text">
          <p v-html="message"></p>
        </div>
        <div class="text-right">
          <a :href="buttonUrl"
             target="_blank"
             class="btn btn-primary">
            {{buttonText}}
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    components: {
    },
    props: {
      title: {
        type: String,
        required: true,
      },
      message: {
        type: String,
        required: true,
      },
      buttonText: {
        type: String,
        required: true,
      },
      buttonUrl: {
        type: String,
        required: true,
      },
    },
    mounted() {
    },
    data() {
      return {
        opened: false,
        widgetId: this.generateID(),
      }
    },
    computed: {
    },
    methods: {
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
    }
  }
</script>
