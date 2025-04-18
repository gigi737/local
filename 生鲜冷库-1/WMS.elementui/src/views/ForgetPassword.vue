<template>
  <div class="forget-password">
    <div class="container">
      <!-- 左侧图片区域 -->
      <div class="left-section">
        <div class="image-wrapper">
          <img src="@/assets/login1.png" alt="Login">
          <div class="overlay"></div>
        </div>
        <div class="tech-circles">
          <span v-for="i in 5" :key="i"></span>
        </div>
      </div>

      <!-- 右侧表单区域 -->
      <div class="right-section">
        <div class="form-container">
          <h2 class="title">
            <span class="tech-line"></span>
            密码重置
            <span class="tech-line"></span>
          </h2>
          
          <el-form ref="loginForm" :model="formData" :rules="rules">
            <el-form-item prop="UserName">
              <el-input 
                v-model.trim="formData.UserName" 
                prefix-icon="el-icon-user"
                placeholder="请输入账号">
              </el-input>
            </el-form-item>

            <el-form-item prop="Email">
              <el-input 
                v-model.trim="formData.Email" 
                prefix-icon="el-icon-message"
                placeholder="请输入邮箱">
              </el-input>
            </el-form-item>

            <el-form-item prop="PhoneNumber">
              <el-input 
                v-model.trim="formData.PhoneNumber" 
                prefix-icon="el-icon-phone"
                placeholder="请输入手机号">
              </el-input>
            </el-form-item>

            <el-form-item prop="Password">
              <el-input 
                v-model.trim="formData.Password" 
                prefix-icon="el-icon-lock"
                type="password"
                placeholder="请输入新密码">
              </el-input>
            </el-form-item>

            <el-form-item prop="Code" class="verify-code">
              <el-input 
                v-model.trim="formData.Code" 
                prefix-icon="el-icon-key"
                placeholder="请输入验证码">
              </el-input>
              <ValidCode ref="ValidCode" class="code-component"></ValidCode>
            </el-form-item>

            <el-button type="primary" class="submit-btn" @click="ForgetBtn">
              重置密码
              <i class="el-icon-right"></i>
            </el-button>
          </el-form>

          <div class="footer-links">
            <RouterLink to="/Login" class="login-link">
              返回登录
              <i class="el-icon-arrow-right"></i>
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',

                Code: ""
            },

            rules: {
                UserName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                Password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确邮箱'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "PhoneNumber": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^1[34578]\d{9}$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确的手机号'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                Code: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            let identifyCode = this.$refs.ValidCode.getCode();

                            if (value != identifyCode) {
                                callback(new Error('请输入正确的验证码'));
                            } else {
                                callback();
                            }
                        }, trigger: 'blur'
                    }
                ]
            }
        }

    },
    created() {

    },
    methods: {

        ForgetBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await this.$Post("/User/ForgetPassword", this.formData)
                    console.log(res);
                    if (res.Success) {
                        this.$message.success("修改密码成功!");
                        this.$router.push({
                            path: "/Login"
                        })
                    }
                    else {

                        this.$refs.ValidCode.refreshCode();
                    }
                } else {
                    this.$message.error("验证不通过")
                    this.$refs.ValidCode.refreshCode();
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped>
.forget-password {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a237e 0%, #0d47a1 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.container {
  width: 1200px;
  height: 700px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
  display: flex;
  overflow: hidden;
  backdrop-filter: blur(10px);
}

.left-section {
  flex: 1;
  position: relative;
  overflow: hidden;
}

.image-wrapper {
  height: 100%;
  position: relative;
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(45deg, rgba(26, 35, 126, 0.7) 0%, rgba(13, 71, 161, 0.7) 100%);
}

.tech-circles span {
  position: absolute;
  border: 2px solid rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  animation: rotate 20s linear infinite;
}

.tech-circles span:nth-child(1) { width: 100px; height: 100px; top: 10%; left: 10%; }
.tech-circles span:nth-child(2) { width: 150px; height: 150px; top: 40%; left: 50%; }
.tech-circles span:nth-child(3) { width: 80px; height: 80px; top: 70%; left: 20%; }
.tech-circles span:nth-child(4) { width: 120px; height: 120px; top: 20%; left: 70%; }
.tech-circles span:nth-child(5) { width: 90px; height: 90px; top: 60%; left: 80%; }

.right-section {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-container {
  width: 400px;
  margin: 0 auto;
}

.title {
  font-size: 28px;
  color: #1a237e;
  margin-bottom: 40px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
}

.tech-line {
  height: 2px;
  width: 30px;
  background: linear-gradient(90deg, #1a237e, #0d47a1);
}

.el-input {
  margin-bottom: 20px;
}

.verify-code {
  display: flex;
  gap: 10px;
}

.code-component {
  flex-shrink: 0;
}

.submit-btn {
  width: 100%;
  height: 50px;
  background: linear-gradient(90deg, #1a237e, #0d47a1);
  border: none;
  font-size: 16px;
  margin-top: 20px;
  transition: transform 0.3s;
}

.submit-btn:hover {
  transform: translateY(-2px);
}

.footer-links {
  margin-top: 20px;
  text-align: center;
}

.login-link {
  color: #1a237e;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s;
}

.login-link:hover {
  color: #0d47a1;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>
