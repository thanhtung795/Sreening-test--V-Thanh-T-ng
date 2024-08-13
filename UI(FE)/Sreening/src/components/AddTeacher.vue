<template>
    <div v-if="showModal" class="modal fade show d-block" style="display: block">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Thêm Giảng Viên</h5>
            <button type="button" class="btn-close" @click="close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="addTeacher">
              <!-- Mã giảng viên -->
              <div class="mb-1">
                <label for="teacherID" class="form-label">Mã giảng viên</label>
                <input
                  type="text"
                  id="teacherID"
                  v-model="newTeacher.teacherID"
                  class="form-control"
                  required
                />
              </div>
              <!-- Tên đầy đủ -->
              <div class="mb-1">
                <label for="fullName" class="form-label">Họ và tên</label>
                <input
                  type="text"
                  id="fullName"
                  v-model="fullName"
                  class="form-control"
                  required
                />
              </div>
              <!-- Trình độ học vấn -->
              <div class="mb-1">
                <label for="qualification" class="form-label">Trình độ học vấn</label>
                <select
                  id="qualification"
                  v-model="newTeacher.qualification"
                  class="form-select"
                  required
                >
                  <option value="Cử nhân cao đẳng">Cử nhân cao đẳng</option>
                  <option value="Cử nhân đại học">Cử nhân đại học</option>
                  <option value="Thạc sĩ">Thạc sĩ</option>
                  <option value="Tiến sĩ">Tiến sĩ</option>
                </select>
              </div>
              <!-- Trạng thái làm việc -->
              <div class="mb-1 d-flex">
                <label class="form-label">Trạng thái làm việc:</label>
                <div class="form-check ms-3">
                  <input
                    type="radio"
                    id="employed"
                    value="true"
                    v-model="newTeacher.employmentStatus"
                    class="form-check-input"
                  />
                  <label for="employed" class="form-check-label">Đang làm việc</label>
                </div>
                <div class="form-check ms-3">
                  <input
                    type="radio"
                    id="retired"
                    value="false"
                    v-model="newTeacher.employmentStatus"
                    class="form-check-input"
                  />
                  <label for="retired" class="form-check-label">Đã nghỉ</label>
                </div>
              </div>
              <!-- Loại giảng viên -->
              <div class="mb-1 d-flex">
                <label class="form-label">Loại giảng viên:</label>
                <div class="form-check ms-3">
                  <input
                    type="radio"
                    id="fullTime"
                    value="true"
                    v-model="newTeacher.teacherType"
                    class="form-check-input"
                  />
                  <label for="fullTime" class="form-check-label">Full-time</label>
                </div>
                <div class="form-check ms-3">
                  <input
                    type="radio"
                    id="partTime"
                    value="false"
                    v-model="newTeacher.teacherType"
                    class="form-check-input"
                  />
                  <label for="partTime" class="form-check-label">Part-time</label>
                </div>
              </div>
              <!-- Lương cơ bản -->
              <div class="mb-1">
                <label for="baseSalary" class="form-label">Lương cơ bản</label>
                <input
                  type="number"
                  id="baseSalary"
                  v-model="newTeacher.baseSalary"
                  class="form-control"
                  required
                />
              </div>
              <!-- Ngày bắt đầu làm việc -->
              <div class="mb-1">
                <label for="employmentDate" class="form-label">Ngày bắt đầu làm việc</label>
                <input
                  type="date"
                  id="employmentDate"
                  v-model="newTeacher.employmentDate"
                  class="form-control"
                  required
                />
              </div>
              <!-- Hình ảnh -->
              <div class="mb-1">
                <label for="image" class="form-label">Hình ảnh (URL)</label>
                <div class="input-group">
                <input
                  type="text"
                  id="image"
                  v-model="newTeacher.image"
                  class="form-control"
                  placeholder="Dán URL hoặc chọn tệp"
                />
                <input
                  type="file"
                  @change="onFileSelected"
                  class="form-control"
                  style="max-width: 90px"
                />
              </div>
              </div>
              <!-- Nút lưu và đóng -->
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" @click="close">Đóng</button>
                <button type="submit" class="btn btn-primary">Thêm giảng viên</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
 <script setup>
 import { ref, watch } from 'vue'
 import axios from 'axios' 
 
 const props = defineProps({
   showModal: Boolean
 })
 
 const emit = defineEmits(['close', 'add'])
 
 const newTeacher = ref({
   teacherID: '',
   lastName: '',
   firstName: '',
   qualification: null, // Để null mặc định
   employmentStatus: true,
   teacherType: true,
   baseSalary: 0,
   employmentDate: '',
   image: ''
 })
 
 const fullName = ref('')
 
 watch(() => props.showModal, (newValue) => {
   if (newValue) {
     resetForm()
   }
 })
 
 const close = () => {
   emit('close')
 }
 const onFileSelected = (event) => {
  const file = event.target.files[0];
  if (file) {
    newTeacher.value.image = file.name;
  }
};
const addTeacher = async () => {
  const names = fullName.value.split(' ')
  newTeacher.value.lastName = names.slice(0, -1).join(' ')
  newTeacher.value.firstName = names.slice(-1).join(' ')

  try {
    const response = await axios.post('http://localhost:9999/saveTeacher', newTeacher.value)
    const result = response.data

    if (result.success) {
      if (newTeacher.value.image) {
        const file = document.querySelector('input[type="file"]').files[0];
        if (file) {
          const formData = new FormData();
          formData.append('file', file);

          const uploadResponse = await axios.post('http://localhost:9999/uploadImage', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });

          if (uploadResponse.data.success) {
            newTeacher.value.image = uploadResponse.data.data; // Cập nhật tên tệp từ server
          } else {
            alert('Tải lên tệp không thành công: ' + uploadResponse.data.message);
          }
        }
      }
      emit('add', newTeacher.value)
      resetForm()
      close()
    } else {
      alert('Thêm giảng viên thất bại: ' + result.message)
    }
  } catch (error) {
    console.error('Error:', error)
    alert('Có lỗi xảy ra khi thêm giảng viên')
  }
}
 const resetForm = () => {
   newTeacher.value = {
     teacherID: '',
     lastName: '',
     firstName: '',
     qualification: null, 
     employmentStatus: true,
     teacherType: true,
     baseSalary: 0,
     employmentDate: '',
     image: ''
   }
   fullName.value = ''
 }
 </script>
 
  
  
  <style scoped>
  .modal.fade.show {
    display: block;
    background: rgba(0, 0, 0, 0.5);
  }
  </style>
  