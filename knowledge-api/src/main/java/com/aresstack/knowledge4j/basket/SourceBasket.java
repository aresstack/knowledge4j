package com.aresstack.knowledge4j.basket;

import com.aresstack.knowledge4j.scope.Fence;
import com.aresstack.knowledge4j.source.SourceRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The basket (Korb): the collection of sources a work is written from and the only interface between the research
 * (Research4j fills it) and the writing (Writing4j writes from it). It holds references to sources that live in the
 * host's stock, never copies; a {@link BasketReader} gives their passages. How the sources were found (a search plan,
 * the user's favourites, a folder) is not part of it. It carries the {@link Fence} it was collected for, mission and
 * Weidezaun with inclusions and exclusions, so the writing gets the same measure as the research and no second copy
 * of it travels beside the basket. Immutable; a source is in it once, whatever its location.
 */
public final class SourceBasket {

    private static final SourceBasket EMPTY = new SourceBasket(Collections.emptyList());

    private final Fence fence;
    private final List<SourceRef> sources;

    /** A basket without a fence yet (an empty mission); {@link #withFence} gives it one. */
    public SourceBasket(List<SourceRef> sources) {
        this(null, sources);
    }

    /** A basket collected for {@code fence}; {@code null} means no fence yet. */
    public SourceBasket(Fence fence, List<SourceRef> sources) {
        this.fence = fence == null ? Fence.of("") : fence;
        List<SourceRef> once = new ArrayList<>();
        if (sources != null) {
            for (SourceRef source : sources) {
                if (source != null && once.stream().noneMatch(source::sameSource)) {
                    once.add(source);
                }
            }
        }
        this.sources = Collections.unmodifiableList(once);
    }

    public static SourceBasket empty() {
        return EMPTY;
    }

    /** Mission and Weidezaun the basket was collected for; an empty mission when none was given. */
    public Fence fence() {
        return fence;
    }

    /** This basket with {@code fence} instead of its own, as the reader widens or narrows the Weidezaun. */
    public SourceBasket withFence(Fence fence) {
        return new SourceBasket(fence, sources);
    }

    /** The sources in the order they were added. */
    public List<SourceRef> sources() {
        return sources;
    }

    public boolean isEmpty() {
        return sources.isEmpty();
    }

    /**
     * This basket with more sources, as a further search adds them; sources already in it stay as they are, and so
     * does the fence.
     */
    public SourceBasket with(List<SourceRef> more) {
        List<SourceRef> all = new ArrayList<>(sources);
        if (more != null) {
            all.addAll(more);
        }
        return new SourceBasket(fence, all);
    }

    /** Sizes and the fingerprint of the fence only. */
    @Override
    public String toString() {
        return "SourceBasket[" + sources.size() + " sources, fence " + fence.basis() + "]";
    }
}
