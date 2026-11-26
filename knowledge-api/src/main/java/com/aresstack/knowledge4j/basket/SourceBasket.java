package com.aresstack.knowledge4j.basket;

import com.aresstack.knowledge4j.source.SourceRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The basket (Korb): the collection of sources a work is written from and the only interface between the research
 * (Research4j fills it) and the writing (Writing4j writes from it). It holds references to sources that live in the
 * host's stock, never copies; a {@link BasketReader} gives their passages. How the sources were found (a search plan,
 * the user's favourites, a folder) is not part of it. Immutable; a source is in it once, whatever its location.
 */
public final class SourceBasket {

    private static final SourceBasket EMPTY = new SourceBasket(Collections.emptyList());

    private final List<SourceRef> sources;

    public SourceBasket(List<SourceRef> sources) {
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

    /** The sources in the order they were added. */
    public List<SourceRef> sources() {
        return sources;
    }

    public boolean isEmpty() {
        return sources.isEmpty();
    }

    /** This basket with more sources, as a further search adds them; sources already in it stay as they are. */
    public SourceBasket with(List<SourceRef> more) {
        List<SourceRef> all = new ArrayList<>(sources);
        if (more != null) {
            all.addAll(more);
        }
        return new SourceBasket(all);
    }

    /** Sizes only. */
    @Override
    public String toString() {
        return "SourceBasket[" + sources.size() + " sources]";
    }
}
