<template>
  <div class="modal fade" id="createTrainingConfigModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createTrainingConfigModalTitle">New Training Strategy</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">

          <fieldset>
            <div class="form-group" :class="{ 'has-danger': error }">
              <label for="exampleModelName">Training Strategy Name</label>
              <input v-model="newTraining.name"
                     type="text"
                     class="form-control"
                     :class="{ 'is-invalid': error }"
                     id="exampleModelName"
                     aria-describedby="exampleModelNameHelp"
                     placeholder="The identifying name of your Model">
              <small id="exampleModelNameHelp" class="form-text text-muted">
                The name by which you will identify your training strategy later.
              </small>
            </div>

            <div class="form-group">
              <div class="custom-control custom-radio">
                <input v-model="newTraining.type" type="radio" id="customRadio1" name="customRadio" value="LOCAL" class="custom-control-input" checked="">
                <label class="custom-control-label" for="customRadio1">Normal Training</label>
              </div>
              <div class="custom-control custom-radio">
                <input v-model="newTraining.type" type="radio" id="customRadio2" name="customRadio" value="SPARK" class="custom-control-input">
                <label class="custom-control-label" for="customRadio2">Training using Spark</label>
              </div>
            </div>


          </fieldset>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary"
                  :disabled="newTraining.name===''"
                  v-on:click="createModel">Create</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        newTraining: {
          modelId: -1,
          name: '',
          type: 'LOCAL',
        },
        error: false,
      }
    },
    computed: {
    },
    methods: {
      openModal(modelid) {
        this.newTraining.modelId = modelid;
        $('#createTrainingConfigModal').modal('show')
      },
      createModel() {
        let loader ;
        $.ajax(
          {
            url: jsRoutes.controllers.NNModelController.createTrainingStrategy().url,
            type: jsRoutes.controllers.NNModelController.createTrainingStrategy().type,
            contentType: "application/json",
            data: JSON.stringify(this.newTraining),
            beforeSend: () => {
              loader = this.$loading.show();
            },
            success: (result,status,xhr) => {
              this.newTraining.modelId= -1;
              this.newTraining.name = '';
              $('#createTrainingConfigModal').modal('hide');
              this.$emit('newTrainingCreated');
            },
            error: (xhr,status,error) => {
              console.error(xhr.responseText);
            },
            complete: () => {
              loader.hide();
            }
          }
        );
      },
    }
  }
</script>
