<template>
  <div class="card border-info mb-3">
    <div class="card-header">
      SGD Updater - Configuration
      <button
        type="button"
        class="close"
        data-toggle="collapse"
        :href="'#'+widgetId"
        v-on:click="opened=!opened"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse" :id="widgetId">
      <div class="card-body">
        <div class="form-group">
          <fieldset>
            <label class="control-label" for="learningRateField">Learning Rate</label>
            <input
              v-model="updater.learningRate"
              v-on:change="notifyChange()"
              class="form-control" id="learningRateField" type="number" placeholder="Readonly input here…">
          </fieldset>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    components: {},
    props: {
      updater: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        widgetId: this.generateID(),
        opened: false,
      }
    },
    methods: {
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      notifyChange() {
        this.$emit('changed');
      }
    }

  }
</script>
