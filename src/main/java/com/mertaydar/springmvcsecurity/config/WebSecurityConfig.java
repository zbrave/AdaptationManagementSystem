package com.mertaydar.springmvcsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mertaydar.springmvcsecurity.authentication.MyDBAuthenticationService;
 
@Configuration
// @EnableWebSecurity = @EnableWebMVCSecurity + Extra features
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    
    @Autowired
    MyDBAuthenticationService myDBAauthenticationService;
    
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Users in memory.
 
//        auth.inMemoryAuthentication().withUser("user1").password("12345").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin1").password("12345").roles("USER", "ADMIN");
 
        // For User in database without Bcrypt.
//        auth.userDetailsService(myDBAauthenticationService);
        
        // Bcrypt
        auth.userDetailsService(myDBAauthenticationService).passwordEncoder(passwordEncoder());
 
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable();
 
        // The pages does not require login
        http.authorizeRequests().antMatchers("/login", "/logout").permitAll();
 
        // /userInfo page requires login as USER or ADMIN.
        // If no login, it will redirect to /login page.
//        http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/", "/welcome", "/sendMail").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')");
        http.authorizeRequests().antMatchers("/myAdapt").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/Student", "/addStudentLesson", "/createStudentLesson", "/deleteStudentLesson", "/createTakingLesson", "/editTakingLesson", "/getTakingLesson", "/getTakingLessons", "/saveTakingLesson", "/takingLessonList", "/uniList", "/createUni", "/deleteUni", "/editUni", "/getUni", "/getUnis", "/saveUni", "/banUser", "/deleteUser", "/formUni", "/formTakingLesson", "/deleteTakingLesson", "/editStudentLesson", "/saveStudentLesson", "/studentLessonList", "/updateStudentLesson", "/createSubstituteLesson", "/deleteSubstituteLesson", "/editSubstituteLesson", "/getSubstituteLesson", "/getSubstituteLessonById", "/getSubstituteLessonCur", "/getSubstituteLessons", "/saveSubstituteLesson", "/substituteLessonList", "/formSubstituteLesson", "/formStudentLesson", "/studentList", "/setToStudent", "/setAdvisor", "/calcAdp", "/createStudent", "/deleteStudent", "/editStudent", "formStudent", "/report", "/getStudent", "/getStudentData", "/getStudentData2", "/addStudent", "/addRules", "/addRules2", "/createRules", "/editRules", "/getPoolLesson", "/getRules", "/rulesList", "/saveRules", "/formRules", "/deleteRules", "/addStudentLesson", "/deleteCurriculum", "/doActive", "/saveCurriculum", "/createDept", "/deleteDept", "/deptList", "/editDept", "/formDept", "/getDept", "/getDepts", "/saveDept", "/admin", "/download/{type}", "/word", "/word2", "/createMark", "/deleteMark", "/markList", "/editMark", "/formMark", "/getMark", "/getMarks", "/saveMark", "/deleteOurmark", "/saveOurmark", "/editOurmark", "/formOurmark", "Ourmark").access("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')");
        http.authorizeRequests().antMatchers("/listAdmin", "/setUserToAdmin", "/setManager", "/setUserToStudent", "/setStudentToUser", "/setAdminToUser", "/listManager", "/listUser", "/listNewUser").access("hasRole('ROLE_ADMIN')");
        // For ADMIN only.
//        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
 
        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will throw.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/welcome")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
 
    }
    
    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}