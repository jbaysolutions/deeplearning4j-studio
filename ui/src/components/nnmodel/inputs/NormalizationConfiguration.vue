<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" href="#collapseConfiguration" v-on:click="opened=!opened">
      <i class="fas fa-random"></i>
      Processors
      <i v-if="updated"
         class="fas fa-info-circle text-warning"
         data-toggle="tooltip"
         data-placement="top"
         title="Modified and requires saving"
         data-original-title="Modified and requires saving"></i>

      <button
              type="button"
              class="close"
      >
        <i v-if="opened" class="fas fa-minus-square"></i>
        <i v-else class="fas fa-plus-square"></i>
      </button>

    </div>
    <div class="collapse" id="collapseConfiguration" >
      <div class="card-body" >
        <div class="row">
          <div class="col-6">

            <div class="form-group">
              <label class="control-label" for="optimizationAlgoField">Optimization Algorithm</label>
              <select
                class="custom-select"
                id="optimizationAlgoField"
                v-model="model.optimizationAlgo"
                v-on:change="updated=true"
              >
                <option v-for="item in helper.optimizationAlgorithmList" :value="item.key">{{item.description}}</option>
              </select>
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
    mounted() {},
    watch: {
      updated() {
        if ( this.updated === true) {
          this.$emit("changed");
        }
      }
    },
    data() {
      return {
        opened: false,
        updated: false,
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
      setUptoDate() {
        this.updated = false;
      }
    }
  }
</script>
