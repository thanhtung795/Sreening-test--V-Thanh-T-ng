<template>
  <div class="container-fluid">
    <h1 class="text-center mb-5">Danh sách giảng viên</h1>
    <div class="input-group mb-3">
      <input
        type="search"
        v-model="searchQuery"
        @keyup.enter="search"
        class="form-control"
        placeholder="Tìm kiếm thông tin..."
        aria-label="Search"
        aria-describedby="search-button"
      />
    </div>
    <div class="themThongTin d-flex justify-content-between align-items-center">
      <!-- Nút Thêm Giảng Viên -->
      <button type="button" class="btn btn-success mb-3" @click="openAddModal">
        Thêm Giảng Viên
      </button>

      <!-- Select -->
      <select
        class="form-select w-25"
        v-model="selectedQualification"
        aria-label="Default select example"
      >
        <option value="">Tất cả</option>
        <option value="Cử nhân cao đẳng">Cử nhân cao đẳng</option>
        <option value="Cử nhân đại học">Cử nhân đại học</option>
        <option value="Thạc sĩ">Thạc sĩ</option>
        <option value="Tiến sĩ">Tiến sĩ</option>
      </select>
    </div>

    <!-- Bảng danh sách giảng viên -->
    <table class="table table-bordered align-middle mb-0 bg-white">
      <thead class="bg-light">
        <tr>
          <th>Mã giảng viên</th>
          <th>Họ và tên</th>
          <th>Loại giảng viên</th>
          <th>Trình độ học vấn</th>
          <th>Lương cơ bản</th>
          <th>Ngày bắt đầu làm việc</th>
          <th>Trạng thái làm việc</th>
          <th>Hình ảnh</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="teacher in filteredTeachers" :key="teacher.teacherID">
          <td>{{ teacher.teacherID }}</td>
          <td>{{ teacher.lastName + ' ' + teacher.firstName }}</td>
          <td>{{ teacher.teacherType ? 'Full-time' : 'Part-time' }}</td>
          <td>{{ teacher.qualification }}</td>
          <td>{{ teacher.baseSalary.toLocaleString() }}</td>
          <td>{{ $filters.formatDate(teacher.employmentDate) }}</td>
          <td>{{ teacher.employmentStatus ? 'Đang làm việc' : 'Đã nghỉ' }}</td>
          <td class="text-center">
            <img
              :src="`/Image/${teacher.image}`"
              :alt="teacher.name"
              style="width: 45px; height: 45px"
              class="rounded-circle"
            />
          </td>
          <td>
            <button type="button" class="btn btn-primary btn-sm" @click="openModal(teacher)">
              Sửa
            </button>
            <button
              type="button"
              class="btn btn-danger btn-sm"
              @click="deleteTeacher(teacher.teacherID)">
              Xóa
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal chỉnh sửa giảng viên -->
    <TeacherModal
      :teacher="selectedTeacher"
      :showModal="showModal"
      @close="showModal = false"
      @save="fetchTeachers"
    />

    <!-- Modal thêm giảng viên -->
    <AddTeacherModal :showModal="showAddModal" @close="showAddModal = false" @add="fetchTeachers" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import TeacherModal from '../components/TeacherModal.vue'
import AddTeacherModal from '../components/AddTeacher.vue'

const teachers = ref([])
const showModal = ref(false)
const showAddModal = ref(false)
const selectedTeacher = ref(null)
const searchQuery = ref('')
const selectedQualification = ref('')

// Hàm kiểm tra nếu giá trị tìm kiếm khớp với giảng viên
const matchesSearchQuery = (teacher) => {
  const query = searchQuery.value.toLowerCase()
  return !query || 
    teacher.teacherID.toLowerCase().includes(query) ||
    (teacher.lastName + ' ' + teacher.firstName).toLowerCase().includes(query) ||
    teacher.qualification.toLowerCase().includes(query)
}

// Hàm kiểm tra nếu trình độ học vấn khớp với lựa chọn
const matchesQualificationFilter = (teacher) => {
  return !selectedQualification.value || 
    teacher.qualification === selectedQualification.value
}

// Tính toán danh sách giảng viên đã được lọc
const filteredTeachers = computed(() => {
  return teachers.value.filter(teacher => 
    matchesSearchQuery(teacher) && matchesQualificationFilter(teacher)
  )
})
const deleteTeacher = async (teacherID) => {
  const isConfirmed = window.confirm("Bạn có chắc chắn muốn xóa giảng viên này không?");

  if (isConfirmed) {
    try {
      const response = await axios.delete(`http://localhost:9999/deleteTeacher?id=${teacherID}`);

      if (response.data.success) {
        console.log('Xóa giảng viên thành công');
        await fetchTeachers();
      } else {
        console.error('Lỗi từ server:', response.data.message);
      }
    } catch (error) {
      if (error.response) {
        console.error('Lỗi từ API:', error.response.data);
      } else if (error.request) {
        console.error('Lỗi yêu cầu:', error.request);
      } else {
        console.error('Lỗi:', error.message);
      }
    }
  } else {
    console.log('Hủy bỏ xóa giảng viên');
  }
}



const fetchTeachers = async () => {
  try {
    const response = await axios.get('http://localhost:9999/getAllTeacher')
    if (response.data.success) {
      teachers.value = response.data.data
    } else {
      console.error(response.data.message)
    }
  } catch (error) {
    console.error('Lỗi khi gọi API:', error)
  }
}

const openModal = (teacher) => {
  selectedTeacher.value = { ...teacher }
  showModal.value = true
}

const openAddModal = () => {
  showAddModal.value = true
}

onMounted(() => {
  fetchTeachers()
})
</script>
