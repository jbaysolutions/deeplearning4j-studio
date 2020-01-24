<template>
  <div class="modal fade" id="trainingInProgressModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="trainingInProgressTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="trainingInProgressTitle">Training in Progress</h5>
        </div>
        <div class="modal-body">
          <p v-for="item in logdata"> {{item}}</p>
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
  export default {
    data() {
      return {
        requestID: null,
        finished: false,
        logdata: [],
      }
    },
    computed: {
    },
    methods: {
      openModal(requestID) {
        this.requestID = requestID;
        this.logdata = [];
        this.finished = false;

        $('#trainingInProgressModal').modal('show')
        this.$options.sockets.onmessage = (event) => {
          let message = JSON.parse(event.data);
          if (message.type==="TRAINING_UPDATE") {
            if (message.uuid===this.requestID) {
              console.log("TRAINING UPDATE DATA !!!! : " + event.data);
              this.logdata.push(message.message);
            }
          }
        }
      },
    }
  }
</script>
