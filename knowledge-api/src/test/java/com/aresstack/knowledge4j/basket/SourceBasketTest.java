package com.aresstack.knowledge4j.basket;

import com.aresstack.knowledge4j.source.SourceKind;
import com.aresstack.knowledge4j.source.SourceLocation;
import com.aresstack.knowledge4j.source.SourceRef;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SourceBasketTest {

    private static final SourceRef A = new SourceRef(SourceKind.INTERNAL_DOCUMENT, "doc-1", "3",
            SourceLocation.WHOLE, "Anleitung");
    private static final SourceRef B = SourceRef.of(SourceKind.WEB_PAGE, "https://example.org/b");

    @Test
    void aSourceIsInTheBasketOnceAndTheFirstReferenceStays() {
        SourceBasket basket = new SourceBasket(List.of(A, B, A.withRevision("4"), A.withTitle("anders")));
        assertEquals(List.of(A, B), basket.sources());
    }

    @Test
    void aFurtherSearchAddsSourcesAndKeepsTheOldOnes() {
        SourceBasket basket = new SourceBasket(List.of(A)).with(List.of(B, A.withRevision("5")));
        assertEquals(List.of(A, B), basket.sources());
        assertTrue(SourceBasket.empty().isEmpty());
        assertEquals("SourceBasket[2 sources]", basket.toString());
    }
}
