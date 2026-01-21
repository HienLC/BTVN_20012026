1. Đăng ký User:
B1: Vào Postman
B2: Đăng ký User (POST)
  * URL: http://localhost:8080/users/register
  * Method: POST
  * Body (raw JSON):
    {
      "full_name": "Le Chi Hien",
      "email": "hien2002@gmail.com",
      "phone_number": "0123456789",
      "password": "abc123",
      "retype_password": "abc123"
    }

2. Lấy danh sách User (GET)
* URL: http://localhost:8080/users
* Method: GET
  Kiểm tra xem password có bị ẩn đi không (theo logic UserResponseDTO)

3. Cập nhật thông tin (PUT)
* URL: http://localhost:8080/users/1
* Method: PUT
* Body (raw JSON):
  {
    "full_name": "Le Chi Hien Updated",
    "phone_number": "0987654321"
  }
