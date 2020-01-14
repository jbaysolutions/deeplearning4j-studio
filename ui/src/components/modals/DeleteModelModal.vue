<template>
  <div class="modal fade" id="deleteModelModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header bg-danger ">
          <h5 class="modal-title" id="createModelModalTitle">Delete Model : {{message}}</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <p>Are you sure you would like to delete model: {{message}} ?</p>
          <p>There is now way to undo this operations!</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-danger" v-on:click="deleteModel">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        modelId: -1,
        message: '',
        error: false,
      }
    },
    computed: {
    },
    methods: {
      openModal(modelId, modelname) {
        this.modelId = modelId;
        this.message = modelname;
        $('#deleteModelModal').modal('show')
      },
      deleteModel() {
        let loader ;
        $.ajax(
          {
            url: jsRoutes.controllers.NNModelController.deleteNNModel(this.modelId).url,
            type: jsRoutes.controllers.NNModelController.deleteNNModel().type,
            beforeSend: () => {
              loader = this.$loading.show();
            },
            success: (result,status,xhr) => {
              this.modelId = -1;
              this.message = '';
              $('#deleteModelModal').modal('hide');
              this.$emit('modelDeleted');
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
