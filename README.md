# Knowledge4j

Shared contracts for [Research4j](https://github.com/aresstack/research4j) and [Writing4j](https://github.com/aresstack/writing4j). Knowledge4j holds interfaces and small value types that a host application implements; it contains no knowledge base, no project management and no model client. Research4j and Writing4j never depend on each other, only on Knowledge4j.

The sources come from [Ask AI](https://github.com/aresstack/askai) main at [d89d1c5f](https://github.com/aresstack/askai/commit/d89d1c5f) (26 Nov 2026). Ask AI itself remains unchanged.

## Modules

| Module | Content |
| --- | --- |
| knowledge-model | `ModelPort` with slots, messages, tools, thinking mode, output format, reservations and resources; `EmbeddingPort` with its three roles; the chat core (`MainModelChat`, `PortMainModelChat`, `BlockingModelCall`, metrics) and the neutral role core (`ModelRole`, `RoleOutput`, `RoleToolCatalog`). Test fixtures: `FakeModelPort`. |
| knowledge-file-support (optional) | Crash-safe UTF-8 writes (`AtomicFiles`) and `CorruptFileException`, shared by the file stores of Research4j and Writing4j. |
| knowledge-api | Source references (`SourceRef`, extensible `SourceKind`, exact `SourceLocation`), `Provenance` and `Classification`; `SourceSearchPort`, `ContentFetchPort`, `PassageSearchPort`, `PassageLookup`, `PassageView`; `SentenceSegmentationPort`; cancellation and progress; the mandatory `TransferPolicy` with the run guard (high-water mark) and guarded ports. Read views for writing live in Writing4j. |
| knowledge-model-support (optional, in progress) | A contract-true capacity planner for hosts without their own model management. |

Package names start with `com.aresstack.knowledge4j`. Java 21.

## Build

```sh
bash gradlew clean build
```

Contract tests are published as JUnit 5 test fixtures, so a host can test its adapters against them. During development, Research4j, Writing4j and hosts include Knowledge4j as a Gradle composite build; no local Maven publication is needed.

## Releases

Nothing is published or tagged without the explicit approval of the product owner.

## License

MIT; see LICENSE.
