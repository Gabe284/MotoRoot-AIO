# Upstream Audit and Porting Plan

## Goal

Build one coherent AIO application from carefully selected and audited components. Prefer adapters and small ports over wholesale repository merges. Keep original projects independently credited and respect each code and asset license.

## Candidate projects

### aOpenFont
Repository: https://github.com/Gabe284/aOpenFont

Evaluate font installation/configuration, Hack family assets, validation, fallback behavior, and existing architecture work. Preserve the existing repository and its history; do not copy unrelated project files by default.

### ZeroMount
Repository: https://github.com/Enginex0/zeromount

Evaluate emoji target discovery, mount integration, status reporting, WebUI settings, and app-specific overrides. Audit installation scripts and conflict behavior before porting. Any logic that automatically runs another module's uninstall script, touches its `remove` marker, or disables it must not be carried into MotoRoot AIO.

### FancyMoji
Repository: https://github.com/newhertz/FancyMoji

Evaluate emoji source selection and UX patterns, including iOS, One UI, AOSP/Noto, and custom-font options. Review source code and font assets separately. A source repository's code license does not automatically grant redistribution rights to included font files.

### KernelSU Next
Repository: https://github.com/KernelSU-Next/KernelSU-Next

Use as the initial root provider. Inspect documented APIs and manager/module integration interfaces. Do not fork, bundle, or modify the root provider unless a concrete requirement and license-compatible implementation justify it.

## Phases

### Phase 0 — Foundation
- Record product scope, target device, safety rules, and third-party sources.
- Select a project-wide license only after reviewing copied code and assets.
- Keep development on a feature branch until the first milestone is reviewed.

### Phase 1 — Read-only core
- Device and OS identity report.
- Root-provider detection and capability report.
- Read-only module inventory and conflict report.
- Structured diagnostic output with sensitive data redacted.
- Unit tests for parsers and capability states.

### Phase 2 — Change planning
- Represent changes as typed operations rather than arbitrary shell strings.
- Show a reviewable plan and require confirmation.
- Record original state and rollback prerequisites.
- Add failure injection tests and plan validation.

### Phase 3 — Font and emoji
- Audit active Android font configuration and ZeroMount implementation.
- Build source catalog and robust TTF/OTF validation.
- Add source manifests with hash, origin, attribution, license, and target policy.
- Discover actual targets and report conflicts.
- Implement transactional apply/restore.
- Add optional app-specific overrides only after system-level behavior is stable.
- Keep GMS font suppression off by default.

### Phase 4 — Other AIO features
- Module management with explicit user-directed actions.
- Performance controls only for verified capabilities and ranges.
- Charging controls only after identifying supported interfaces and safe bounds.
- Diagnostics, export, and update compatibility checks.

### Phase 5 — Validation
- Static analysis, unit tests, and reproducible builds.
- Emulator/disposable-device tests.
- Exact Moto G 2025 Android 16 validation.
- Test partial failures, interrupted operations, stale targets, conflicts, malformed assets, and rollback.
- Publish a clear support matrix and recovery instructions.

## Acceptance criteria for initial release

- Read-only inventory does not mutate the device.
- No automatic module removal or disabling.
- No stock font deletion or broad recursive app-private replacement.
- Every mutation has a target plan, confirmation, verification, and tested recovery path.
- Invalid or unlicensed font assets are rejected or excluded.
- The app does not weaken SELinux, boot verification, or account-protection settings.
- Documentation clearly distinguishes tested behavior from planned behavior.
