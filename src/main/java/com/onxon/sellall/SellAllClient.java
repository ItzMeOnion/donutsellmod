package com.onxon.sellall;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class SellAllClient implements ClientModInitializer {

    // Number of player-inventory slots at the end of the handler:
    // 27 main inventory + 9 hotbar. Verify against the real /sell GUI.
    private static final int PLAYER_SLOT_COUNT = 36;

    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof HandledScreen<?> handled)) {
                return;
            }
            if (!isSellMenu(handled)) {
                return;
            }

            Screens.getButtons(screen).add(
                ButtonWidget.builder(Text.literal("Sell All"), button -> sellAll(handled))
                    .dimensions(scaledWidth / 2 + 90, 4, 60, 20)
                    .build()
            );
        });
    }

    /**
     * TODO: replace with a real check once the /sell GUI is known.
     * Options: match the exact title text, or check the handler's slot count.
     */
    private static boolean isSellMenu(HandledScreen<?> screen) {
        return screen.getTitle().getString().toLowerCase().contains("sell");
    }

    private static void sellAll(HandledScreen<?> screen) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.interactionManager == null) {
            return;
        }

        ScreenHandler handler = screen.getScreenHandler();
        int syncId = handler.syncId;
        int start = Math.max(0, handler.slots.size() - PLAYER_SLOT_COUNT);

        for (int i = start; i < handler.slots.size(); i++) {
            Slot slot = handler.getSlot(i);
            if (slot.hasStack()) {
                // Shift-click: moves the stack into the sell area.
                mc.interactionManager.clickSlot(syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
            }
        }
        // TODO: if the sell menu needs a confirm click, send it here.
    }
}
