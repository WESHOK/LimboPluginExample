package ru.mdxd44.example;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import java.io.IOException;
import net.elytrium.limboapi.api.Limbo;
import net.elytrium.limboapi.api.LimboFactory;
import net.elytrium.limboapi.api.chunk.Dimension;
import net.elytrium.limboapi.api.chunk.VirtualWorld;
import net.elytrium.limboapi.api.event.LoginLimboRegisterEvent;

@Plugin(
    id = "limboapi_example",
    name = "LimboAPI Example",
    version = "1.0.0",
    authors = {
        "mdxd44"
    },
    dependencies = {
        @Dependency(id = "limboapi")
    }
)
public class Example {

  private final LimboFactory factory;

  private Limbo virtualServer;

  @Inject
  public Example(ProxyServer server) {
    this.factory = (LimboFactory) server.getPluginManager().getPlugin("limboapi").flatMap(PluginContainer::getInstance).orElseThrow();
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) throws IOException {
    VirtualWorld world = this.factory.createVirtualWorld(
        Dimension.OVERWORLD,
        17, 32, 15,
        0F, 0F
    );
    //new SchematicFile(this.getClass().getResourceAsStream("/limbo.schematic")).toWorld(this.factory, world, 0, 0, 0);
    this.virtualServer = this.factory.createLimbo(world);
  }

  @Subscribe(order = PostOrder.LATE)
  public void onLogin(LoginLimboRegisterEvent event) {
    event.addCallback(() -> this.virtualServer.spawnPlayer(event.getPlayer(), new Handler()));
  }
}
