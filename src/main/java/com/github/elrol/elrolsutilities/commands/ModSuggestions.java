package com.github.elrol.elrolsutilities.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.github.elrol.elrolsutilities.Main;
import com.github.elrol.elrolsutilities.api.IElrolAPI;
import com.github.elrol.elrolsutilities.data.PlayerData;
import com.github.elrol.elrolsutilities.init.PermRegistry;
import com.github.elrol.elrolsutilities.init.Ranks;
import com.github.elrol.elrolsutilities.libs.ClaimFlagKeys;
import com.github.elrol.elrolsutilities.libs.Permissions;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;

public class ModSuggestions {
    public static CompletableFuture<Suggestions> suggestHomes(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        PlayerData data;
        try {
            data = Main.database.get((context.getSource()).getPlayerOrException().getUUID());
        }
        catch (CommandSyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return ISuggestionProvider.suggest(data.getHomeNames(), builder);
    }

    public static CompletableFuture<Suggestions> suggestPerms(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Main.permRegistry.getPerms(), builder);
    }

    public static CompletableFuture<Suggestions> suggestRanks(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Ranks.rankMap.keySet(), builder);
    }

    public static CompletableFuture<Suggestions> suggestWarps(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Main.serverData.warpMap.keySet(), builder);
    }

    public static CompletableFuture<Suggestions> suggestRankups(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        try {
            List<String> suggest = Main.database.get(context.getSource().getPlayerOrException().getUUID()).getDomRank().getNextRanks();
            ISuggestionProvider.suggest(suggest, builder);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return ISuggestionProvider.suggest(new ArrayList<>(), builder);
    }

    public static CompletableFuture<Suggestions> suggestKits(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Main.kitMap.keySet(), builder);
    }

    public static CompletableFuture<Suggestions> suggestPlayers(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        ArrayList<String> userNames = new ArrayList<>();
        for (PlayerData data : Main.database.getDatabase().values()) {
            if (data.username.isEmpty()) continue;
            userNames.add(data.username);
        }
        return ISuggestionProvider.suggest(userNames, builder);
    }

    public static CompletableFuture<Suggestions> suggestFlags(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(ClaimFlagKeys.list(), builder);
    }

    public static CompletableFuture<Suggestions> suggestClearlagTypes(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Arrays.asList("hostile", "passive", "item"), builder);
    }

    public static CompletableFuture<Suggestions> suggestJails(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggest(Main.serverData.jailMap.keySet(), builder);
    }
}

