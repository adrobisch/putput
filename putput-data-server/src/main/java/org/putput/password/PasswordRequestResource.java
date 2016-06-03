package org.putput.password;

import brainslug.flow.context.BrainslugContext;
import brainslug.flow.execution.instance.FlowInstance;
import brainslug.flow.execution.instance.InstanceSelector;
import brainslug.flow.expression.Value;
import org.putput.api.model.PasswordReset;
import org.putput.api.model.PasswordResetConfirmation;
import org.putput.api.resource.PasswordRequest;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static brainslug.flow.execution.property.ExecutionProperties.newProperties;

@Service
@Transactional
public class PasswordRequestResource extends BaseResource implements PasswordRequest {

  @Autowired
  PasswordService passwordService;

  @Autowired
  BrainslugContext brainslugContext;

  @Override
  public PostPasswordRequestResponse postPasswordRequest(PasswordReset passwordReset) throws Exception {
    brainslugContext.startFlow(ForgotPasswordFlow.id, newProperties()
        .with(ForgotPasswordFlow.emailAddress, passwordReset.getEmail()));

    return PostPasswordRequestResponse.withNoContent();
  }

  @Override
  public PostPasswordRequestConfirmationResponse postPasswordRequestConfirmation(PasswordResetConfirmation entity) throws Exception {
    Optional<FlowInstance> instance = getInstanceForCode(entity);

    if (instance.isPresent()) {
      brainslugContext.signalEvent(
          ForgotPasswordFlow.confirmationReceivedId,
          instance.get().getIdentifier(),
          ForgotPasswordFlow.id);

      return PostPasswordRequestConfirmationResponse.withNoContent();
    }

    return PostPasswordRequestConfirmationResponse.withBadRequest();
  }

  private Optional<FlowInstance> getInstanceForCode(PasswordResetConfirmation entity) {
    InstanceSelector passwordFlowWithCode = new InstanceSelector().withProperty(ForgotPasswordFlow.confirmationCode, new Value<String>(entity.getCode()));
    Collection<? extends FlowInstance> instances =
        brainslugContext.findInstances(passwordFlowWithCode);
    return Optional.of(instances.iterator().next());
  }
}
