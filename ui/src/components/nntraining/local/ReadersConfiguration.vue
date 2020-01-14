<template>
  <div class="card mb-3">
    <div class="card-header" data-toggle="collapse" href="#collapseConfiguration" v-on:click="opened=!opened">
      <i class="fas fa-cogs"></i>
      Model General Configuration
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

            <div class="form-group">

              <div class="custom-control custom-switch">
                <input type="checkbox" class="custom-control-input" id="randomSeedField" checked="" v-model="model.randomSeed" v-on:change="updated=true">
                <label class="custom-control-label" for="randomSeedField">Use Random Seed?</label>
              </div>
              <fieldset v-if="!model.randomSeed">
                <label class="control-label" for="seedField">Seed to use</label>
                <input class="form-control" id="seedField" type="number"
                       placeholder="Put seed to use here…"
                       v-model="model.seed"
                       :disabled="model.randomSeed"
                       v-on:change="updated=true"
                >
              </fieldset>
            </div>
          </div>

          <div class="col-6">
            <div class="form-group">
              <label class="control-label" for="defaultUpdaterField">Default Updater for every Layer</label>
              <select
                class="form-control"
                id="defaultUpdaterField"
                v-model="model.defaultUpdater"
                v-on:change="updated=true"
              >
                <option :value="null">No default updater selected</option>
                <option v-for="item in helper.updaterList" :value="item.key">{{item.description}}</option>
              </select>
            </div>

            <AdamUpdaterEditor
              :updater="model.isAdamWrapper"
              @changed="updated=true"
              v-if="model.defaultUpdater==='ADAM'"/>

            <NesterovsUpdaterEditor
              :updater="model.isNesterovsWrapper"
              @changed="updated=true"
              v-if="model.defaultUpdater==='NESTEROVS'"
            />

            <SGDUpdaterEditor
              :updater="model.isSgdWrapper"
              @changed="updated=true"
              v-if="model.defaultUpdater==='SGD'"
            />
          </div>
        </div>
        <!--<div class="row">
          <div class="col-12 text-right">
            <button type="button" class="btn btn-secondary" v-on:click="createModel">Discard</button>
            <button type="button" class="btn btn-primary" v-on:click="createModel">Create</button>
          </div>
        </div>-->
      </div>
    </div>
  </div>
</template>

<script>

  import AdamUpdaterEditor from './updater/AdamUpdaterEditor'
  import NesterovsUpdaterEditor from './updater/NesterovsUpdaterEditor'
  import SGDUpdaterEditor from './updater/SGDUpdaterEditor'

  import {mapGetters} from 'vuex';

  export default {
    components: {
      AdamUpdaterEditor,
      NesterovsUpdaterEditor,
      SGDUpdaterEditor,
    },
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
