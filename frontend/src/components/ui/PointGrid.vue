<template>
  <v-container>
    <v-snackbar
      v-model="snackbar.status"
      :timeout="snackbar.timeout"
      :color="snackbar.color"
    >
      <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
        Close
      </v-btn>
    </v-snackbar>

    <div class="panel">
      <div class="gs-bundle-of-buttons" style="max-height:10vh;">
        <!-- 결제 버튼: 클릭 시 paymentDialog 열기 -->
        <v-btn @click="openPaymentDialog" class="contrast-primary-text" small color="primary">
          <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>결제
        </v-btn>
        <!-- 결제 취소 버튼 제거 -->
      </div>

      <PointsList @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></PointsList>

      <div class="mb-5 text-lg font-bold"></div>
      <div class="table-responsive">
        <v-table>
          <thead>
            <tr>
              <th>구독신청일</th>
              <th>구독해제 예정일</th>
              <th>신청한 구독 가격</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(val, idx) in value"
              @click="changeSelectedRow(val)"
              :key="val"
              :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;' : ''"
            >
              <td class="font-semibold">{{ idx + 1 }}</td>
              <td class="whitespace-nowrap" label="PointBalance">{{ val.pointBalance }}</td>
              <td class="whitespace-nowrap" label="PointRechargeDate">{{ val.pointRechargeDate }}</td>
              <td class="whitespace-nowrap" label="PointSpendDate">{{ val.pointSpendDate }}</td>
              <td class="whitespace-nowrap" label="UserId">{{ val.userId }}</td>
              <v-row class="ma-0 pa-4 align-center">
                <v-spacer></v-spacer>
                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
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
            <div style="color:white; font-size:17px; font-weight:700;">Point 등록</div>
            <v-spacer></v-spacer>
            <v-icon color="white" small @click="closeDialog()">mdi-close</v-icon>
          </v-toolbar>
          <v-card-text>
            <Point
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

      <v-dialog v-model="editDialog" transition="dialog-bottom-transition" width="35%">
        <v-card>
          <v-toolbar color="primary" class="elevation-0 pa-4" height="50px">
            <div style="color:white; font-size:17px; font-weight:700;">Point 수정</div>
            <v-spacer></v-spacer>
            <v-icon color="white" small @click="closeDialog()">mdi-close</v-icon>
          </v-toolbar>
          <v-card-text>
            <div>
              <Number label="pointId" v-model="selectedRow.pointId" :editMode="true" />
              <Number label="PointBalance" v-model="selectedRow.pointBalance" :editMode="true" />
              <Date label="PointRechargeDate" v-model="selectedRow.pointRechargeDate" :editMode="true" />
              <Date label="PointSpendDate" v-model="selectedRow.pointSpendDate" :editMode="true" />
              <Number label="UserId" v-model="selectedRow.userId" :editMode="true" />
              <v-divider class="border-opacity-100 my-divider"></v-divider>
              <v-layout row justify-end>
                <v-btn width="64px" color="primary" @click="save">수정</v-btn>
              </v-layout>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-col>

    <!-- 결제 다이얼로그 -->
    <v-dialog v-model="paymentDialog" max-width="400">
      <v-card>
        <v-card-title>결제하기</v-card-title>
        <v-card-text>
          결제 내용을 입력하세요.
          <!-- 여기에 결제 폼 컴포넌트 등 넣으면 됩니다 -->
        </v-card-text>
        <v-card-actions>
          <v-btn text @click="closePaymentDialog">취소</v-btn>
          <v-btn color="primary" @click="submitPayment">결제</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue';

export default {
  name: 'pointGrid',
  mixins: [BaseGrid],
  data: () => ({
    path: 'points',
    paymentDialog: false, // 결제 다이얼로그 상태
  }),
  methods: {
    openPaymentDialog() {
      this.paymentDialog = true;
    },
    closePaymentDialog() {
      this.paymentDialog = false;
    },
    submitPayment() {
      alert('결제 완료!');
      this.closePaymentDialog();
    },
  },
};
</script>
