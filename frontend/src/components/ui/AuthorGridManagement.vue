<template>
  <v-container>
    <v-snackbar
      v-model="snackbar.status"
      :timeout="snackbar.timeout"
      :color="snackbar.color"
    >
      {{ snackbar.text }}
      <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
        Close
      </v-btn>
    </v-snackbar>

    <div class="panel">
      <div class="gs-bundle-of-buttons" style="max-height:10vh;">
        <v-btn
          :disabled="!selectedRow"
          style="margin-left: 5px;"
          color="green"
          small
          @click="approveSelectedAuthor"
        >
          승인
        </v-btn>
        <v-btn
          :disabled="!selectedRow"
          style="margin-left: 5px;"
          color="red"
          small
          @click="rejectSelectedAuthor"
        >
          반려
        </v-btn>
      </div>

      <AuthorList
        @search="search"
        style="margin-bottom: 10px; background-color: #ffffff;"
      ></AuthorList>

      <div class="mb-5 text-lg font-bold"></div>

      <div class="table-responsive">
        <v-table>
          <thead>
            <tr>
              <th>순서</th>
              <th>이름</th>
              <th>닉네임</th>
              <th>연락처</th>
              <th>이메일</th>
              <th>포트폴리오Url</th>
              <th>승인여부</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(val, idx) in value"
              @click="changeSelectedRow(val)"
              :key="val.authorId || idx"
              :style="val === selectedRow ? 'background-color: rgba(var(--v-theme-primary), 0.2) !important;' : ''"
            >
              <td class="font-semibold" label="authorId">{{ idx + 1 }}</td>
              <td class="whitespace-nowrap" label="AuthorName">{{ val.authorName }}</td>
              <td class="whitespace-nowrap" label="AuthorNickname">{{ val.authorNickname }}</td>
              <td class="whitespace-nowrap" label="PhoneNumber">{{ val.phoneNumber }}</td>
              <td class="whitespace-nowrap" label="Email">{{ val.email }}</td>
              <td class="whitespace-nowrap" label="PortfolioUrl">{{ val.portfolioUrl }}</td>
              <td class="whitespace-nowrap" label="Status">{{ val.status }}</td>
              <v-row class="ma-0 pa-4 align-center">
                <v-spacer></v-spacer>
                <Icon style="cursor: pointer;" icon="mi:delete" @click.stop="deleteRow(val)" />
              </v-row>
            </tr>
          </tbody>
        </v-table>
      </div>
    </div>

    <v-col>
      <v-dialog v-model="openDialog" transition="dialog-bottom-transition" width="35%">
        <v-card>
          <v-toolbar color="primary" class="elevation-0 pa-4" height="50px">
            <div style="color:white; font-size:17px; font-weight:700;">Author 등록</div>
            <v-spacer></v-spacer>
            <v-icon color="white" small @click="closeDialog()">mdi-close</v-icon>
          </v-toolbar>
          <v-card-text>
            <Author
              :offline="offline"
              :isNew="!value.idx"
              :editMode="true"
              :inList="false"
              v-model="newValue"
              @add="append"
            />
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-col>
  </v-container>
</template>

<script>
import axios from 'axios';
import BaseGrid from '../base-ui/BaseGrid.vue';
import AuthorRepository from '../repository/AuthorRepository';

export default {
  name: 'authorGrid',
  mixins: [BaseGrid],
  data() {
    return {
      path: 'authors',
      repository: null,
    };
  },
  created() {
    this.repository = new AuthorRepository(axios);
    this.search(); // 최초 데이터 조회
  },
  methods: {
    async approveSelectedAuthor() {
      if (!this.selectedRow) return;
      
      try {
        if(this.selectedRow.status!=null){
           throw("이미 처리된 요청입니다");
        }
        await this.repository.updateAuthorApproval(this.selectedRow._links.self.href.split('/').pop(), 'approve');
        this.success('승인 처리되었습니다.');
        await this.search();
      } catch (e) {
        this.error(e);
      }
    },
    async rejectSelectedAuthor() {
      if (!this.selectedRow) return;
      try {
        if(this.selectedRow.status!=null){
           throw("이미 처리된 요청입니다");
        }
        await this.repository.updateAuthorApproval(this.selectedRow._links.self.href.split('/').pop(), 'reject');
        this.success('반려 처리되었습니다.');
        await this.search();
      } catch (e) {
        this.error(e);
      }
    },
  },
};
</script>
