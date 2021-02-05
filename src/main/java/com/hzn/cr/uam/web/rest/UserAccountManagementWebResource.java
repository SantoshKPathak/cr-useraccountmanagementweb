package com.hzn.cr.uam.web.rest;

import com.hzn.cr.uam.service.UserAccountManagementService;
import com.hzn.cr.uam.service.dto.UsrUserDTO;
import com.hzn.cr.uam.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * REST controller for managing {@link com.hzn.cr.uam.domain.UsrUser}.
 *
 * @author santosh pathak
 */
@RestController
@RequestMapping("/api")
public class UserAccountManagementWebResource {
  private final Logger log = LoggerFactory.getLogger(UserAccountManagementWebResource.class);

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  @Value("${application.api.uam.url}")
  private String uamUrl;

  private static final String CR_ENTITY_NAME = "User";

  private final UserAccountManagementService userAccountManagementService;

  public UserAccountManagementWebResource(UserAccountManagementService userAccountManagementService) {
    this.userAccountManagementService = userAccountManagementService;
  }

  /**
   * {@code PUT  /cr-user/activate} : Activates an existing User account if expired.
   *
   * @param usrUserDTO the usrUserDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and result message
   */
  @PutMapping("/cr-user/activate")
  public ResponseEntity<Map> activateUser(@Valid @RequestBody UsrUserDTO usrUserDTO) {
    log.debug("Web request to activate CR UserId : {}", usrUserDTO.getUsrId());

    if (usrUserDTO.getUsrId() == null) {
      throw new BadRequestAlertException("Invalid usrId", CR_ENTITY_NAME, "usrId null");
    }
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<UsrUserDTO> requestEntity = new HttpEntity<>(usrUserDTO);
    ResponseEntity<Map> result = restTemplate.exchange(uamUrl, HttpMethod.PUT, requestEntity, Map.class);
    log.debug("Response from API for userId: {} , StatusCode: {}", usrUserDTO.getUsrId(), result.getStatusCode());
    log.debug("Response from API for userId: {} , body: {}", usrUserDTO.getUsrId(), result.getBody().toString());
    return result;
  }
}
