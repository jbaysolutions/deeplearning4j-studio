<template>
  <div class="card border-success mb-3">
    <div class="card-header"   data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened">
      <strong>Input Layer Configuration (For Dense Layer)</strong>
      <span class="badge badge-pill badge-dark">Num. Input Nodes: {{model.inputNodes}}</span>
      <button
        type="button"
        class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>
    </div>
    <div class="collapse" :id="widgetId">
      <div class="card-body">
        <div class="row">
          <div class="col-6">
            <div class="form-group">
              <fieldset>
                <label class="control-label" for="numNodesField">Number of Input Nodes</label>
                <input
                  v-model="model.inputNodes"
                  v-on:change="notifyChange()"
                  class="form-control" id="numNodesField" type="number"
                  placeholder="Number of input nodes for the Model" >
              </fieldset>
            </div>
          </div>
          <div class="col-6">

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    components: {},
    props: {
      model: {
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
    computed: {
      ...mapGetters('dl4j',
          {
            helper: 'getHelperData',
          },
      ),
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
