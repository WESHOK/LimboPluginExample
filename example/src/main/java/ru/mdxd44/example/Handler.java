package ru.mdxd44.example;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import net.elytrium.limboapi.api.Limbo;
import net.elytrium.limboapi.api.LimboSessionHandler;
import net.elytrium.limboapi.api.player.LimboPlayer;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

public class Handler implements LimboSessionHandler {

  @MonotonicNonNull
  private LimboPlayer player;

  @Override
  public void onSpawn(Limbo server, LimboPlayer player) {
    this.player = player;
    //player.disableFalling();
    //player.setGameMode(GameMode.SPECTATOR);
    try {
      player.sendImage(ImageIO.read(Objects.requireNonNull(Example.class.getResourceAsStream("/photo_2022-01-04_02-04-51.jpg"))));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onChat(String chat) {
    if (chat.equals("go back i wan't to be monke")) {
      this.player.disconnect();
    }
  }
}
