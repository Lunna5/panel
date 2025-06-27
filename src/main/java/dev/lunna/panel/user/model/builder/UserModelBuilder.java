package dev.lunna.panel.user.model.builder;

import com.google.auto.value.AutoBuilder;
import dev.lunna.panel.user.model.UserModel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@AutoBuilder(ofClass = UserModel.class)
public abstract class UserModelBuilder {
  public static UserModelBuilder builder() {
    return new AutoBuilder_UserModelBuilder();
  }

  public abstract UserModelBuilder setUsername(@NotNull String username);
  public abstract UserModelBuilder setPassword(@NotNull String password);
  public abstract UserModelBuilder setEmail(@NotNull String email);
  public abstract UserModelBuilder setFirstName(@NotNull String firstName);
  public abstract UserModelBuilder setLastName(@Nullable String lastName);
  public abstract UserModelBuilder setTotpSecret(@Nullable String totpSecret);
  public abstract UserModel build();
}
