<template>
    <div class="login-container">
        <div class="login-box">
            <!-- 左侧图片区域 -->
            <div class="login-left">
                <div class="overlay"></div>
                <img src="@/assets/login1.png" alt="login background">
                <div class="welcome-text">
                    <h1>Welcome <br>Back</h1>
                    <p><br></p>
                    <p>Enter your personal details<br>and start your journey.</p>
                </div>
            </div>

            <!-- 右侧登录表单区域 -->
            <div class="login-right">
                <div class="login-form-container">
                    <h2>登录冷库管理系统</h2>
                    <p class="subtitle">请输入您的账号信息</p>

                    <el-form ref="loginForm" :model="formData" :rules="rules">
                        <el-form-item prop="UserName">
                            <el-input v-model.trim="formData.UserName" placeholder="请输入账号" prefix-icon="el-icon-user">
                            </el-input>
                        </el-form-item>

                        <el-form-item prop="Password">
                            <el-input type="password" v-model.trim="formData.Password" placeholder="请输入密码"
                                prefix-icon="el-icon-lock">
                            </el-input>
                        </el-form-item>

                        <el-form-item prop="RoleType" class="role-select">
                            <el-radio-group v-model="formData.RoleType">
                                <el-radio v-for="item in roleOptions" :key="item.Code" :label="item.Code">
                                    {{ item.Label }}
                                </el-radio>
                            </el-radio-group>
                        </el-form-item>

                        <el-form-item prop="Code" class="verify-code">
                            <div class="code-input-group">
                                <el-input v-model.trim="formData.Code" placeholder="请输入验证码">
                                </el-input>
                                <ValidCode ref="ValidCode" class="code-image"></ValidCode>
                            </div>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="LoginBtn" class="login-btn">
                                登 录
                            </el-button>
                        </el-form-item>
                    </el-form>

                    <div class="form-footer">
                        <RouterLink :to="{ path: '/ForgetPassword' }" class="forget-pwd">
                            忘记密码?
                        </RouterLink>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue';
import store from '@/store';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',
                RoleType: "",
                Code: ""
            },
            roleOptions: [],
            rules: {
                UserName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                Password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                RoleType: [
                    { required: true, message: '请选择角色', trigger: 'blur' },
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
        this.GetRoleTypeApi();
    },
    methods: {
        async GetRoleTypeApi() {
            let { Data: { Items } } = await this.$Post("/Select/RoleType");

            this.roleOptions = Items

        },
        LoginBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await store.dispatch("Login", this.formData);
                    if (res.Success) {
                        this.$message.success("登录成功!");
                        this.$router.push({
                            path: "/Admin"
                        })

                    }
                    else {

                        this.$refs.ValidCode.refreshCode();
                    }
                } else {
                    this.$message.error("登录验证不通过")
                    this.$refs.ValidCode.refreshCode();
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped>
.login-container {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #1a237e 0%, #0d47a1 100%);
}

.login-box {
    width: 1200px;
    height: 600px;
    display: flex;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 15px 25px rgba(0, 0, 0, 0.2);
}

.login-left {
    position: relative;
    flex: 1;
    background: #000;
    overflow: hidden;
}

.login-left img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    opacity: 0.7;
}

.overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(45deg, rgba(26, 35, 126, 0.7) 0%, rgba(13, 71, 161, 0.7) 100%);
    z-index: 1;
}

.welcome-text {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
    color: white;
    z-index: 2;
}

.welcome-text h1 {
    font-size: 2.5em;
    margin-bottom: 20px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.welcome-text p {
    font-size: 1.1em;
    opacity: 0.9;
}

.login-right {
    flex: 1;
    background: white;
    padding: 40px;
    display: flex;
    align-items: center;
}

.login-form-container {
    width: 100%;
    max-width: 400px;
    margin: 0 auto;
}

.login-form-container h2 {
    font-size: 1.8em;
    color: #333;
    margin-bottom: 10px;
}

.subtitle {
    color: #666;
    margin-bottom: 30px;
}

.el-input {
    margin-bottom: 20px;
}

.role-select {
    margin: 20px 0;
}

.verify-code .code-input-group {
    display: flex;
    gap: 10px;
}

.code-image {
    flex-shrink: 0;
}

.login-btn {
    width: 100%;
    height: 45px;
    font-size: 16px;
    margin-top: 20px;
    background: linear-gradient(45deg, #1a237e, #0d47a1);
    border: none;
}

.login-btn:hover {
    background: linear-gradient(45deg, #0d47a1, #1a237e);
    transform: translateY(-1px);
    transition: all 0.3s ease;
}

.form-footer {
    margin-top: 20px;
    text-align: right;
}

.forget-pwd {
    color: #1a237e;
    text-decoration: none;
    font-size: 14px;
}

.forget-pwd:hover {
    text-decoration: underline;
}
</style>