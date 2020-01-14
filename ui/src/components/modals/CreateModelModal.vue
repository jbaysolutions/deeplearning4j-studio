<template>
  <div class="modal fade" id="createModelModal"
       data-backdrop="static"
       tabindex="-1" role="dialog"
       aria-labelledby="createModelModalTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="createModelModalTitle">Create New Model</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">

          <fieldset>
              <div class="form-group" :class="{ 'has-danger': error }">
                <label for="exampleModelName">Model Name</label>
                <input v-model="newModel.name"
                       type="text"
                       class="form-control"
                       :class="{ 'is-invalid': error }"
                       id="exampleModelName"
                       aria-describedby="exampleModelNameHelp"
                       placeholder="The identifying name of your Model">
                <small id="exampleModelNameHelp" class="form-text text-muted">
                  The name by which you will identify your model later.
                </small>
              </div>
          </fieldset>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary"
                  :disabled="newModel.name===''"
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
        newModel: {
          name: '',
          type: 'MULTILAYER',
        },
        error: false,
      }
    },
    computed: {
    },
    methods: {
      openModal() {
        $('#createModelModal').modal('show')
      },
      createModel() {
        let loader ;
        $.ajax(
          {
            url: jsRoutes.controllers.NNModelController.createNewModel().url,
            type: jsRoutes.controllers.NNModelController.createNewModel().type,
            contentType: "application/json",
            data: JSON.stringify(this.newModel),
            beforeSend: () => {
              loader = this.$loading.show();
            },
            success: (result,status,xhr) => {
              this.newModel.name = '';
              $('#createModelModal').modal('hide');
              this.$emit('newModelCreated');
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
