<template>
  <div>
    <h1>Models</h1>
    <p class="lead">
      Your Machine Learning Models
    </p>

    <div class="row">
      <div class="col-12 text-right">
        <p class="bs-component">
          <button type="button" class="btn btn-primary btn-sm"
                  v-on:click="openCreateModal"
          >
            <i class="fas fa-plus"></i>
            New Model
          </button>
        </p>
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <table class="table table-dark table-striped">
          <thead>
            <tr class="table-primary">
              <th scope="col">#</th>
              <th scope="col">Model</th>
              <th scope="col">Type</th>
              <th scope="col">Status</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(model, index) in models">
              <th scope="row">{{index+1}}</th>
              <td>{{model.name}}</td>
              <td>Neural Network</td>
              <td>{{model.status}}</td>
              <td width="1">
                <div class="btn-group" role="group" aria-label="Basic example">
                  <button type="button" class="btn btn-primary btn-sm" style="white-space: nowrap;">
                    <i class="fas fa-play-circle"></i>
                    Start
                  </button>
                  <router-link class="btn btn-primary btn-sm" style="white-space: nowrap;" :to="{name: 'ModelEditor', params: {id: model.modelId}}">
                    <i class="fab fa-codepen"></i>
                    Open
                  </router-link>
                  <button type="button"
                          v-on:click="openDeleteModal(model.modelId, model.name)"
                          class="btn btn-danger btn-sm" style="white-space: nowrap;">
                    <i class="fas fa-trash"></i>
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Modal -->
    <CreateModelModal
      ref="createModelModal"
      @newModelCreated="loadModels"
    />

    <DeleteModelModal
      ref="deleteModelModal"
      @modelDeleted="loadModels"
    />

  </div>
</template>
<script>
  import assets from '../assets'
  import CreateModelModal from '../components/modals/CreateModelModal'
  import DeleteModelModal from '../components/modals/DeleteModelModal'

  export default {
    components: {
      CreateModelModal,
      DeleteModelModal,
    },
    data() {
      return {
        models: [],
      };
    },
    computed: {
    },
    mounted() {
      this.loadModels();
    },
    methods: {
      openCreateModal() {
        this.$refs.createModelModal.openModal()
      },
      openDeleteModal(modelId, modelname) {
        this.$refs.deleteModelModal.openModal(modelId, modelname)
      },
      loadModels() {
        //console.log('---- ' + jsRoutes);
        let loader;
        jsRoutes.controllers.NNModelController.getAllModels().ajax(
          {
            beforeSend: () => {
              loader = this.$loading.show();
            },
            success: (result,status,xhr) => {
                var json = $.parseJSON(xhr.responseText);
                this.models = json;
                console.log(xhr.responseText);
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
