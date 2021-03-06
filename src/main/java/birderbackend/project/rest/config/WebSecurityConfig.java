package birderbackend.project.rest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import birderbackend.project.rest.security.controller.JwtAuthenticationEntryPoint;
import birderbackend.project.rest.security.controller.JwtAuthenticationTokenFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                // Enable cors
                .cors().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/**",  "/refresh").permitAll()
                .antMatchers(HttpMethod.POST,"/registers").permitAll()
                .antMatchers(HttpMethod.POST,"/registers_farm_employee").hasRole("OWNER")
                .antMatchers(HttpMethod.GET,"/viewProfileDetail/{id}").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
//                .antMatchers(HttpMethod.POST,"/updateProfileDetail/{id}").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
                .antMatchers(HttpMethod.POST,"/updateProfileDetail/{id}").hasAnyRole("ADMIN", "OWNER")
                .antMatchers(HttpMethod.GET,"/viewFarmList").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/viewFarmEmployeeList").hasAnyRole("ADMIN", "OWNER")
                .antMatchers(HttpMethod.GET,"/searchFarmList").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/searchFarmEmployeeList").hasAnyRole("ADMIN", "OWNER")
                .antMatchers(HttpMethod.POST,"/deleteAccount/{id}").hasAnyRole("ADMIN", "OWNER")
                .antMatchers(HttpMethod.GET,"/viewBirdList/{id}").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
                .antMatchers(HttpMethod.GET,"/searchBirdList").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
                .antMatchers(HttpMethod.POST,"/createBirdDetail").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
                .antMatchers(HttpMethod.GET,"/viewBirdDetail/{id}").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
                .antMatchers(HttpMethod.POST,"/updateBirdDetail/{id}").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
                .antMatchers(HttpMethod.POST,"/deleteBird/{id}").hasAnyRole("ADMIN", "OWNER", "EMPLOYEE")
//                .antMatchers(HttpMethod.GET,"/events").permitAll()
//                .antMatchers(HttpMethod.GET,"/organizers").permitAll()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .anyRequest().authenticated();

        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().frameOptions().disable();
        // disable page caching
        httpSecurity.headers().cacheControl();
    }


}