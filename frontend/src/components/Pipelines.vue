<template>
  <div class="page-content">
    <div class="row">
      <div class="col-sm-8">
        <h1>{{ title }}</h1>
      </div>
      <div class="col-sm-4">
        <b-btn v-b-modal.add-pipeline-modal
               type="button"
               class="btn btn-success btn-lg float-right add-button"
        >Create new pipeline
        </b-btn>
      </div>
    </div>
    <ul class="list-items">
      <li v-for="item in pipelines" :key="item.ID">
        <div class="list-item">
          <b-btn v-b-modal.delete-pipeline-modal
                 type="button"
                 class="btn btn-danger btn-md float-right action-button"
                 @click="selectedId = item.ID"
                 v-b-tooltip.hover title="Delete Pipeline">

            <i class="fa fa-trash" />
          </b-btn>
          <b-btn v-b-modal.clone-pipeline-modal
                 type="button"
                 class="btn btn-secondary btn-md float-right action-button"
                 @click="selectedId = item.ID"
                 v-b-tooltip.hover title="Clone Pipeline">

            <i class="fa fa-copy" />
          </b-btn>
          <b-btn
              type="button"
              class="btn btn-secondary btn-md float-right action-button"
              @click="startPipeline(item.ID)"
              v-b-tooltip.hover title="Start Pipeline">
            <i class="fa fa-play" />
          </b-btn>
          <div>
            <router-link :to="{path: '/project/' + projectId + '/pipelines/' + item.ID + '/editor'}"
                         class="list-item-link" v-b-tooltip.hover title="Click to edit pipeline"
            >{{ item.Name ? item.Name : "Unnamed Pipeline"}}
            </router-link>
          </div>
          <div>last changed: {{ formatISODate(item.LastChanged) }}</div>
        </div>
      </li>
    </ul>

    <!-- modals -->

    <b-modal
        id="add-pipeline-modal"
        ref="createModal"
        title="New Pipeline"
        @ok="createPipeline"
        @shown="clearName"
    >
      <form @submit.stop.prevent="handleSubmit">
        <b-form-input type="text" placeholder="New Pipeline Name" v-model="name"/>
        <span class="error-message">{{ modalErrorMessage }}</span>
      </form>

    </b-modal>

    <b-modal
        id="clone-pipeline-modal"
        ref="cloneModal"
        title="Name for cloned Pipeline"
        @ok="clonePipeline"
        @shown="clearName"
    >
      <form @submit.stop.prevent="handleSubmit">
        <b-form-input type="text" placeholder="New Pipeline Name" v-model="name"/>
      </form>
      <span class="error-message">{{ modalErrorMessage }}</span>
    </b-modal>

    <b-modal id="delete-pipeline-modal" ref="deleteModal" title="Delete Pipeline?" @ok="deletePipeline"/>
  </div>
</template>

<script>
import { createCurrentTimeFormatted, formatISODate } from '../utils';

export default {
  name: "Pipelines",
  data() {
    return {
      title: "Existing Pipelines",
      name: "",
      selectedId: null,
      pipelines: [],
      projectId: this.$router.history.current.fullPath.split('/')[2],
      modalErrorMessage: ""
    };
  },
  async created() {
    try {
      const pid = this.$router.history.current.fullPath.split('/')[2];
      const resp = await this.$backendCli.getPipelines(pid);
      this.pipelines = resp.data;
    } catch (e) {
      this.$notifyError(e);
    }
  },
  methods: {
    clearName() {
      this.name = "";
      this.modalErrorMessage = ""
    },
    showModalErrorMessage(messageOrError){
      this.modalErrorMessage = messageOrError.message || messageOrError.errorMessage || messageOrError;
    },
    formatISODate(date) {
      return formatISODate(date);
    },
    async createPipeline(evt) {
      evt.preventDefault();
      if (!this.name) {
        this.showModalErrorMessage("Please enter a name");
      } else {
        const pipeline = {
          Name: this.name,
          LastChanged: createCurrentTimeFormatted()
        };
        try {
          const resp = await this.$backendCli.createPipeline(this.projectId, pipeline);
          this.pipelines.push(resp.data);
          this.clearName;
          this.$refs.createModal.hide();
        } catch (e) {
          this.showModalErrorMessage(e);
        }
      }
    },
    async startPipeline(id) {
      try {
        const resp = await this.$backendCli.startPipeline(this.projectId, id);
        this.$notifyInfo("Pipeline started")
      } catch (e) {
        this.$notifyError(e);
      }
    },
    async clonePipeline(evt) {
      evt.preventDefault();
      var id = this.selectedId;
      if (!this.name) {
        this.showModalErrorMessage("Please enter a name");
      } else {
        try {
          const originalPipeline = this.pipelines.find(pip => pip.ID === id);
          var template = Object.assign({}, originalPipeline);
          template.ID = null;
          template.PipelineSteps.forEach(step => step.ID = null);
          template.PipelineSteps.forEach(step => step.Params.forEach(param => param.ID = null));
          template.Name = this.name;
          template.LastChanged = createCurrentTimeFormatted();
          const resp = await this.$backendCli.createPipeline(this.projectId, template);
          this.pipelines.push(resp.data);
          this.clearName;
          this.$refs.cloneModal.hide();
        } catch (e) {
          this.showModalErrorMessage(e);
        }
      }
    },
    async deletePipeline(evt) {
      evt.preventDefault();
      var id = this.selectedId;
      try {
        await this.$backendCli.deletePipeline(this.selectedId, id);
        this.pipelines = this.pipelines.filter(pip => pip.ID !== id);
        this.$refs.deleteModal.hide();
      } catch (e) {
        this.$notifyError(e);
      }
    }
  }
};
</script>
