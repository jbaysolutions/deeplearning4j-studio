<template>
  <div class="card border-success mb-3">
    <div class="card-header" data-toggle="collapse" :href="'#'+widgetId" v-on:click="opened=!opened" style="cursor: pointer">
      <i class="fas fa-trophy"></i>
      Past Training Results
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
        <div class="row">
          <div class="col-12">
            <table class="table table-dark table-striped">
              <thead>
              <tr class="table-primary">
                <th scope="col">Version</th>
                <th scope="col">Date</th>
                <th scope="col">Epoch</th>
                <th scope="col">Score</th>
                <th scope="col"></th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item, index) in training.results">
                <th scope="row">{{item.version}}</th>
                <td>{{item.executionDate}}</td>
                <td>{{item.bestEpoch}}</td>
                <td>{{item.bestScore}}</td>
                <td class="text-right">
                  <a class="btn btn-primary btn-sm"
                          data-toggle="tooltip" data-placement="top" title="" data-original-title="Download saved Network Zip"
                          :href='generateDownloadURL(item.resultId)'
                          target="_blank"
                  >
                    <i class="fas fa-file-download"></i>
                  </a>
                  <button class="btn btn-primary btn-sm"
                          data-toggle="tooltip" data-placement="top" title="" data-original-title="View training Chart"
                  >
                    <i class="fas fa-chart-area"></i>
                  </button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  import {generateID, generateTrainingRequest} from '../../../../assets/webSocketRequests'

  export default {
    components: {
    },
    mounted() {

    },
    computed: {
      ...mapGetters('dl4j',
        {
          modeldata: 'getCurrentModel',
          training: 'getCurrentTrainingStrategy',
        },
      ),
      trainer() {
        if (this.training && this.training.rawStrategy) {
          return this.training.rawStrategy;
        } else {
          return {};
        }
      },
    },
    data() {
      return {
        widgetId: this.generateID(),
        opened: false,
      }
    },
    methods: {
      ...mapActions('dl4j',
        {
          getTrainingStrategyResultsList: 'getTrainingStrategyResultsList',
        }
      ),
      generateID() {
        return (Math.random()+"").replace("0.","AAA");
      },
      generateDownloadURL(id) {
        return jsRoutes.controllers.NNModelController.downloadTrainingResultsFile(id).url
      }
    }
  }
</script>
