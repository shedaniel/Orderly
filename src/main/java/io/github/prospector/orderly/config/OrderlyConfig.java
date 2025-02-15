package io.github.prospector.orderly.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.prospector.orderly.Orderly;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
public class OrderlyConfig {

    /**
     * whether the mod is enabled
     */
	private boolean draw = true;

	private int maxDistance = 24;

    /**
     * whether to render health bars when the HUD is disabled by pressing F1
     */
	private boolean renderInF1 = false;

    /**
     * scale modifier for the health bar
     */
    private float healthBarScale = 1.0F;

	private double heightAbove = 0.6;

    /**
     * whether to draw the background
     */
	private boolean drawBackground = true;
	private int backgroundPadding = 2;
	private int backgroundHeight = 6;
	private int barHeight = 4;
	private int plateSize = 25;
	private int plateSizeBoss = 50;
	private boolean showAttributes = true;
	private boolean showArmor = true;
	private boolean groupArmor = true;
	private boolean colorByType = false;

    /**
     * (negative) offset for the health bar text relative to the entity name
     */
	private int hpTextHeight = 14;
	private boolean showMaxHP = true;
	private boolean showCurrentHP = true;
	private boolean showPercentage = true;
	private boolean showOnPlayers = true;
	private boolean showOnBosses = true;
	private boolean showOnlyFocused = false;
	private boolean enableDebugInfo = true;
	private static final transient String[] blacklistDefaults = new String[]{"minecraft:shulker", "minecraft:armor_stand", "minecraft:cod", "minecraft:salmon", "minecraft:pufferfish", "minecraft:tropical_fish", "illuminations:firefly"};
	private Set<String> blacklist = Sets.newHashSet(blacklistDefaults);

    static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(String.format("config.%s.title", Orderly.MOD_ID));
        OrderlyConfig config = OrderlyConfigManager.getConfig();
        builder.getOrCreateCategory("general")
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("draw", config.canDraw()).setDefaultValue(true).setSaveConsumer(b -> config.draw = b).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("maxDistance", config.getMaxDistance()).setDefaultValue(24).setSaveConsumer(i -> config.maxDistance = i).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("renderInF1", config.canRenderInF1()).setDefaultValue(false).setSaveConsumer(b -> config.renderInF1 = b).build())
                .addEntry(ConfigEntryBuilder.create().startFloatField("healthBarScale", config.getHealthBarScale()).setDefaultValue(1.0F).setSaveConsumer(d -> config.healthBarScale = d).build())
                .addEntry(ConfigEntryBuilder.create().startDoubleField("heightAbove", config.getHeightAbove()).setDefaultValue(0.6D).setSaveConsumer(d -> config.heightAbove = d).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("drawBackground", config.drawsBackground()).setDefaultValue(true).setSaveConsumer(b -> config.drawBackground = b).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("backgroundPadding", config.getBackgroundPadding()).setDefaultValue(2).setSaveConsumer(i -> config.backgroundPadding = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("backgroundHeight", config.getBackgroundHeight()).setDefaultValue(6).setSaveConsumer(i -> config.backgroundHeight = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("barHeight", config.getBarHeight()).setDefaultValue(4).setSaveConsumer(i -> config.barHeight = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("plateSize", config.getPlateSize()).setDefaultValue(25).setSaveConsumer(i -> config.plateSize = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("plateSizeBoss", config.getPlateSizeBoss()).setDefaultValue(50).setSaveConsumer(i -> config.plateSizeBoss = i).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showAttributes", config.canShowAttributes()).setDefaultValue(true).setSaveConsumer(b -> config.showAttributes = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showArmor", config.canShowArmor()).setDefaultValue(true).setSaveConsumer(b -> config.showArmor = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("groupArmor", config.canShowGroupArmor()).setDefaultValue(true).setSaveConsumer(b -> config.groupArmor = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("colorByType", config.colorByType()).setDefaultValue(false).setSaveConsumer(b -> config.colorByType = b).build())
                .addEntry(ConfigEntryBuilder.create().startIntField("hpTextHeight", config.getHpTextHeight()).setDefaultValue(14).setSaveConsumer(i -> config.hpTextHeight = i).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showMaxHP", config.canShowMaxHP()).setDefaultValue(true).setSaveConsumer(b -> config.showMaxHP = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showCurrentHP", config.canCurrentHP()).setDefaultValue(true).setSaveConsumer(b -> config.showCurrentHP = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showPercentage", config.canShowPercentage()).setDefaultValue(true).setSaveConsumer(b -> config.showPercentage = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showOnPlayers", config.canShowOnPlayers()).setDefaultValue(true).setSaveConsumer(b -> config.showOnPlayers = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showOnBosses", config.canShowOnBosses()).setDefaultValue(true).setSaveConsumer(b -> config.showOnBosses = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("showOnlyFocused", config.showingOnlyFocused()).setDefaultValue(false).setSaveConsumer(b -> config.showOnlyFocused = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("enableDebugInfo", config.isDebugInfoEnabled()).setDefaultValue(false).setSaveConsumer(b -> config.enableDebugInfo = b).build())
                .addEntry(ConfigEntryBuilder.create().startStrList("blacklist", Lists.newArrayList(config.getBlacklist())).setDefaultValue(Lists.newArrayList(blacklistDefaults)).setExpended(true).setSaveConsumer(strings -> config.blacklist = strings.stream().filter(Identifier::isValid).map(Identifier::new).map(Identifier::toString).collect(Collectors.toSet())).build());
        builder.setSavingRunnable(OrderlyConfigManager::save);
        return builder.build();
    }

	public void toggleDraw() {
		draw = !draw;
	}

	public boolean canDraw() {
		return draw;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public boolean canRenderInF1() {
		return renderInF1;
	}

	public double getHeightAbove() {
		return heightAbove;
	}

	public boolean drawsBackground() {
		return drawBackground;
	}

	public int getBackgroundPadding() {
		return backgroundPadding;
	}

	public int getBackgroundHeight() {
		return backgroundHeight;
	}

	public int getBarHeight() {
		return barHeight;
	}

	public int getPlateSize() {
		return plateSize;
	}

	public int getPlateSizeBoss() {
		return plateSizeBoss;
	}

	public boolean canShowAttributes() {
		return showAttributes;
	}

	public boolean canShowArmor() {
		return showArmor;
	}

	public boolean canShowGroupArmor() {
		return groupArmor;
	}

	public boolean colorByType() {
		return colorByType;
	}

	public int getHpTextHeight() {
		return hpTextHeight;
	}

	public boolean canShowMaxHP() {
		return showMaxHP;
	}

	public boolean canCurrentHP() {
		return showCurrentHP;
	}

	public boolean canShowPercentage() {
		return showPercentage;
	}

	public boolean canShowOnPlayers() {
		return showOnPlayers;
	}

	public boolean canShowOnBosses() {
		return showOnBosses;
	}

	public boolean showingOnlyFocused() {
		return showOnlyFocused;
	}

	public boolean isDebugInfoEnabled() {
		return enableDebugInfo;
	}

	public Set<String> getBlacklist() {
		return blacklist;
	}

    public float getHealthBarScale() {
        return healthBarScale;
    }

//            v_maxDistance = builder.define("Max Distance", maxDistance);
//            v_renderInF1 = builder.define("Render with Interface Disabled (F1)", renderInF1);
//            v_heightAbove = builder.define("Height Above Mob", heightAbove);
//            v_drawBackground = builder.define("Draw Background", drawBackground);
//            v_backgroundPadding = builder.define("Background Padding", backgroundPadding);
//            v_backgroundHeight = builder.define("Background Height", backgroundHeight);
//            v_barHeight = builder.define("Health Bar Height", barHeight);
//            v_plateSize = builder.define("Plate Size", plateSize);
//            v_plateSizeBoss = builder.define("Plate Size (Boss)", plateSizeBoss);
//            v_showAttributes = builder.define("Show Attributes", showAttributes);
//            v_showArmor = builder.define("Show Armor", showArmor);
//            v_groupArmor = builder.define("Group Armor (condense 5 iron icons into 1 diamond icon)", groupArmor);
//            v_colorByType = builder.define("Color Health Bar by Type (instead of health percentage)", colorByType);
//            v_hpTextHeight = builder.define("HP Text Height", hpTextHeight);
//            v_showMaxHP = builder.define("Show Max HP", showMaxHP);
//            v_showCurrentHP = builder.define("Show Current HP", showCurrentHP);
//            v_showPercentage = builder.define("Show HP Percentage", showPercentage);
//            v_showOnPlayers = builder.define("Display on Players", showOnPlayers);
//            v_showOnBosses = builder.define("Display on Bosses", showOnBosses);
//            v_showOnlyFocused = builder.define("Only show the health bar for the entity looked at", showOnlyFocused);
//            v_enableDebugInfo = builder.define("Show Debug Info with F3", enableDebugInfo);
//            v_blacklist = builder.comment("Blacklist uses entity IDs, not their display names. Use F3 to see them in the Orderly bar.")


}
