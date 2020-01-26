<template>
  <div class="modal fade" id="trainingInProgressModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="trainingInProgressTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="trainingInProgressTitle">Training in Progress</h5>
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

  import TrainingProgressWidget from '../../nntraining/general/charts/TrainingProgressWidget'

  export default {
    components: {
      TrainingProgressWidget,
    },
    data() {
      return {
        requestID: null,
        finished: false,
        datacollection: {
          labels: [],
          datasets: [
            {
              label: 'Score',
              backgroundColor: '#DF691A',
              data: [],
              fill: false,
              lineTension: 0,
              borderColor: '#DF691A',
              borderWidth: 1,
              pointStyle: 'rectRot',
              pointRadius: 5,
              pointBorderColor: 'rgb(0, 0, 0)'
            }
          ]
        },
      }
    },
    computed: {
    },
    methods: {
      openModal(requestID) {
        this.requestID = requestID;
        this.finished = false;
        this.datacollection = {
          labels: [],
          datasets: [
            {
              label: 'Score',
              backgroundColor: '#DF691A',
              data: [],
              fill: false,
              lineTension: 0,
              borderColor: '#DF691A',
              borderWidth: 1,
              pointStyle: 'rectRot',
              pointRadius: 5,
              pointBorderColor: 'rgb(0, 0, 0)'
            }
          ]
        };

        $('#trainingInProgressModal').modal('show')
        this.$options.sockets.onmessage = (event) => {
          let message = JSON.parse(event.data);
          if (message.type==="TRAINING_UPDATE") {
            if (message.uuid===this.requestID) {
              console.log("TRAINING UPDATE DATA !!!! : " + event.data);
              this.datacollection.labels.push(message.epoch);
              this.datacollection.datasets[0].data.push(message.score);
              this.$refs.realTimeChart.refreshChart();
            }
          }
          if (message.type==="TRAINING_FINISHED") {
            if (message.uuid===this.requestID) {
              console.log("TRAINING FINISHED DATA !!!! : " + event.data);
              this.logdata.push(message.message);
              this.finished = true;
            }
          }
        }
      },
    }
  }
</script>
