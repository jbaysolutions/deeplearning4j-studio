<template>
  <div class="modal fade" id="trainingInProgressModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="trainingInProgressTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="trainingInProgressTitle">{{modalTitle}}</h5>
        </div>
        <div class="modal-body">


          <div class="row">
            <div class="col-12">

            </div>
            <div class="col-12">
              <TrainingProgressWidget style="height: 400px;" ref="realTimeChart" :chart-data="datacollection" />
            </div>
          </div>

        </div>
        <div class="modal-footer">
          <button v-if="finished" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <!--<button type="button" class="btn btn-primary"
                  :disabled="newModel.name===''"
                  v-on:click="createModel">Create</button>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  import {mapActions, mapGetters} from 'vuex';
  import TrainingProgressWidget from '../../nntraining/general/charts/TrainingProgressWidget'

  export default {
    components: {
      TrainingProgressWidget,
    },
    data() {
      return {
        modalTitle: 'Training in Progress',
        requestID: -1,
        finished: false,
        datacollection: {
          labels: [],
          datasets: [
            {
              label: 'Recall',
              backgroundColor: '#DF691A',
              data: [],
              fill: true,
              lineTension: 0,
              borderColor: '#DF691A',
              borderWidth: 1,
              pointStyle: 'rectRot',
              pointRadius: 5,
              pointBorderColor: 'rgb(0, 0, 0)'
            },
            {
              label: 'F1',
              backgroundColor: '#82df3d',
              data: [],
              fill: true,
              lineTension: 0,
              borderColor: '#82df3d',
              borderWidth: 1,
              pointStyle: 'rectRot',
              pointRadius: 5,
              pointBorderColor: 'rgb(0, 0, 0)'
            },
            {
              label: 'Precision',
              backgroundColor: '#2b9edf',
              data: [],
              fill: true,
              lineTension: 0,
              borderColor: '#2b9edf',
              borderWidth: 1,
              pointStyle: 'rectRot',
              pointRadius: 5,
              pointBorderColor: 'rgb(0, 0, 0)'
            },
            {
              label: 'Accuracy',
              backgroundColor: '#c356df',
              data: [],
              fill: true,
              lineTension: 0,
              borderColor: '#c356df',
              borderWidth: 1,
              pointStyle: 'rectRot',
              pointRadius: 5,
              pointBorderColor: 'rgb(0, 0, 0)'
            }
          ]
        },
      }
    },
    mounted() {
      this.$options.sockets.onmessage = (event) => {
        let message = JSON.parse(event.data);
        if (message.type==="TRAINING_UPDATE") {
          if (message.uuid===this.requestID) {
            console.log("TRAINING UPDATE DATA !!!! : " + event.data);
            this.datacollection.labels.push(message.epoch);
            this.datacollection.datasets[0].data.push(message.recall);
            this.datacollection.datasets[1].data.push(message.f1);
            this.datacollection.datasets[2].data.push(message.precision);
            this.datacollection.datasets[3].data.push(message.accuracy);
            this.$refs.realTimeChart.refreshChart();
          }
        }
        if (message.type==="TRAINING_FINISHED") {
          if (message.uuid===this.requestID) {
            console.log("TRAINING FINISHED DATA !!!! : " + event.data);
            this.modalTitle = 'Training Finished';
            this.finished = true;
            this.getTrainingStrategy(this.training.strategyId);
          }
        }
      }
    },
    computed: {
      ...mapGetters('dl4j',
        {
          training: 'getCurrentTrainingStrategy',
        },
      ),
    },
    methods: {
      ...mapActions('dl4j',
        {
          getTrainingStrategy: 'getTrainingStrategy',
        }
      ),
      openModal(requestID) {
        this.modalTitle = 'Training in Progress';
        this.requestID = requestID;
        this.finished = false;
        this.datacollection.labels= [];
        this.datacollection.datasets[0].data= [];
        this.datacollection.datasets[1].data= [];
        this.datacollection.datasets[2].data= [];
        this.datacollection.datasets[3].data= [];

        $('#trainingInProgressModal').modal('show');
      },
    }
  }
</script>
