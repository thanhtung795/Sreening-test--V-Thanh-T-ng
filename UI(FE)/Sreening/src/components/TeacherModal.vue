<template>
  <div v-if="show" class="modal fade show d-block" style="display: block">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Sửa Thông Tin Giảng Viên</h5>
          <button type="button" class="btn-close" @click="close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveChanges">
            <!-- Hiển thị ID -->
            <div class="mb-1">
              <label for="teacherID" class="form-label">Mã giảng viên</label>
              <input
                type="text"
                id="teacherID"
                v-model="currentTeacher.teacherID"
                class="form-control"
                disabled
              />
            </div>
            <!-- Tên đầy đủ -->
            <div class="mb-1">
              <label for="fullName" class="form-label">Họ và tên</label>
              <input type="text" id="fullName" v-model="fullName" class="form-control" required />
            </div>
            <div class="mb-1">
              <label for="qualification" class="form-label">Trình độ học vấn</label>
              <select
                id="qualification"
                v-model="currentTeacher.qualification"
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
                  v-model="currentTeacher.employmentStatus"
                  class="form-check-input"
                />
                <label for="employed" class="form-check-label">Đang làm việc</label>
              </div>
              <div class="form-check ms-3">
                <input
                  type="radio"
                  id="retired"
                  value="false"
                  v-model="currentTeacher.employmentStatus"
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
                  v-model="currentTeacher.teacherType"
                  class="form-check-input"
                />
                <label for="fullTime" class="form-check-label">Full-time</label>
              </div>
              <div class="form-check ms-3">
                <input
                  type="radio"
                  id="partTime"
                  value="false"
                  v-model="currentTeacher.teacherType"
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
                v-model="currentTeacher.baseSalary"
                class="form-control"
              />
            </div>
            <!-- Ngày bắt đầu làm việc -->
            <div class="mb-1">
              <label for="employmentDate" class="form-label">Ngày bắt đầu làm việc</label>
              <input
                type="date"
                id="employmentDate"
                v-model="formattedEmploymentDate"
                class="form-control"
              />
            </div>
            <!-- Hình ảnh -->
            <div class="mb-1">
              <label for="image" class="form-label">Hình ảnh (URL)</label>
              <div class="input-group">
                <input
                  type="text"
                  id="image"
                  v-model="currentTeacher.image"
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
              <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed } from 'vue'
import axios from 'axios'

const props = defineProps({
  teacher: Object,
  showModal: Boolean
})

const emit = defineEmits(['close', 'save'])

const currentTeacher = ref({ ...props.teacher })
const show = ref(props.showModal)

// Cập nhật currentTeacher khi props.teacher thay đổi
watch(
  () => props.teacher,
  (newValue) => {
    currentTeacher.value = { ...newValue }
  },
  { immediate: true }
)

// Cập nhật show khi props.showModal thay đổi
watch(
  () => props.showModal,
  (newValue) => {
    show.value = newValue
  }
)

// Tính toán fullName từ lastName và firstName
const fullName = computed({
  get() {
    return `${currentTeacher.value.lastName} ${currentTeacher.value.firstName}`
  },
  set(value) {
    const names = value.split(' ')
    currentTeacher.value.lastName = names.slice(0, -1).join(' ')
    currentTeacher.value.firstName = names.slice(-1).join(' ')
  }
})

// Chuyển đổi ngày từ DATETIME sang định dạng YYYY-MM-DD
const formattedEmploymentDate = computed({
  get() {
    const date = new Date(currentTeacher.value.employmentDate)
    return date.toISOString().split('T')[0]
  },
  set(value) {
    currentTeacher.value.employmentDate = new Date(value).toISOString()
  }
})
const close = () => {
  emit('close')
}

const onFileSelected = (event) => {
  const file = event.target.files[0];
  if (file) {
    currentTeacher.value.image = file.name;
  }
};
const saveChanges = async () => {
  try {
    // Cập nhật fullName
    const [lastName, firstName] = fullName.value.split(' ')
    currentTeacher.value.lastName = lastName
    currentTeacher.value.firstName = firstName

    // Lưu thông tin giảng viên trước
    const response = await axios.put('http://localhost:9999/updateTeacher', currentTeacher.value)
    const result = response.data

    if (result.success) {
      // Nếu có hình ảnh, tải lên hình ảnh sau khi lưu thông tin giảng viên
      if (currentTeacher.value.image) {
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
            currentTeacher.value.image = uploadResponse.data.data; // Cập nhật tên tệp từ server
          } else {
            alert('Tải lên tệp không thành công: ' + uploadResponse.data.message);
          }
        }
      }

      emit('save', currentTeacher.value)
      close()
    } else {
      alert('Lưu thay đổi thất bại: ' + result.message)
    }
  } catch (error) {
    console.error('Error:', error)
    alert('Có lỗi xảy ra khi lưu thay đổi')
  }
}

</script>

<style scoped>
.modal.fade.show {
  display: block;
  background: rgba(0, 0, 0, 0.5);
}
</style>
