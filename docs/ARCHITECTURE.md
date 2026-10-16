# Architecture

## Product boundary

MotoRoot AIO is a device-aware root management and customization frontend, not a replacement root implementation. The first supported configuration is Motorola Moto G 2025 (XT2513-1), Android 16, stock Kansas firmware, with KernelSU Next present.

## Layered design

### 1. Native Android user interface
- The initial app is a native Kotlin Android application.
- The current screen exposes a manually triggered read-only audit; it is an early skeleton, not a complete management UI.
- The planned interface will present provider status, device facts, module inventory, feature configuration, conflicts, plans, logs, and rollback status.
- Separates discovery from apply actions.
- Shows the exact targets and operations before a privileged change.
- Does not infer success from a command being launched; it verifies the resulting state.

### 2. Application / policy layer
- Validates requested operations against device capabilities and policy.
- Produces a reviewable change plan.
- Requires explicit confirmation for mutations.
- Serializes conflicting operations and maintains a transaction record.
- Refuses unsupported, ambiguous, or untrusted targets.

### 3. Root-provider adapter
- The initial audit uses the conventional `su -c` entry point with a fixed, source-defined, read-only script.
- KernelSU Next is the primary target, but the app must not assume that the presence of `su` proves a particular provider.
- Provider detection must report evidence and uncertainty, and use documented/supported interfaces where available.
- Reports capabilities rather than assuming that root access implies every operation is supported.
- Keeps provider-specific implementation out of feature modules.
- Must not weaken root-provider authentication or silently change provider configuration.

### 4. Device capability profile
- Identifies model, Android API level, build fingerprint, kernel, ABI, mount behavior, and relevant feature availability.
- Uses capability probes and version checks instead of model-name checks alone.
- Treats unknown values as unsupported until verified.
- Separates read-only identification from mutable tuning controls.

### 5. Feature modules
- **Module manager:** read-only inventory first; explicit, user-directed enable/disable operations later.
- **Fonts and emoji:** source catalog, font validation, target discovery, conflict reporting, reversible overlays, and optional app-specific rules.
- **Performance:** expose only controls that have been validated for the detected device and firmware.
- **Charging:** discover supported interfaces and bounds before any write; no guessed sysfs paths or values.
- **Diagnostics:** collect minimal data, redact secrets, and export a report for review.

## Proposed repository organization

```text
core/
  root-provider/
  device-profile/
  capabilities/
  change-plan/
  transactions/
features/
  module-manager/
  fonts/
  emoji/
  performance/
  charging/
  diagnostics/
ui/
tests/
docs/
LICENSES/
```

This is a target structure, not an assertion that these directories or implementations already exist.

## Change transaction model

1. Discover current state without mutation.
2. Validate source files, targets, permissions, provider capability, and compatibility.
3. Generate a plan showing each intended change and any conflict.
4. Obtain explicit user confirmation.
5. Save enough original state to restore the exact prior configuration.
6. Apply narrowly scoped changes with per-step verification.
7. If any required step fails, attempt rollback and report both the original failure and rollback outcome.
8. Keep a durable, privacy-conscious audit record.

Rollback cannot be promised for an operation until its recovery mechanism is implemented and tested. Destructive operations should not be offered merely because they are technically possible.

## Font and emoji integration

- Reuse useful ZeroMount concepts only after auditing the current upstream implementation.
- Keep font-source selection and validation independent from the mechanism used to expose fonts to Android.
- Discover actual active font configuration and target paths; do not blindly mount one font onto every legacy or vendor filename.
- Validate custom TTF/OTF files, record SHA-256, and preserve source attribution and license data.
- Report module conflicts without uninstalling, disabling, or marking another module for removal.
- Keep per-app overrides opt-in, individually scoped, and reversible.
- Keep GMS font suppression off by default and separate from ordinary emoji replacement.
- Do not automatically delete stock fonts, `/data/fonts`, GMS font directories, or app-private font files.

## Technology selection

The initial app uses Kotlin, Android's native Activity/UI APIs, Android SDK 36, and JDK 17. This keeps the first prototype small and avoids importing ZeroMount's full WebUI or root orchestration engine. The project may adopt a richer UI framework later if it materially improves maintainability. The build is configured for a debug APK and a read-only unit-test suite; CI status is the source of truth for whether a given revision compiles.

## Non-goals for the first milestone

- Replacing KernelSU Next or bundling a second root manager.
- Automatic module removal or blanket module disabling.
- Pixel/device identity spoofing.
- Security-protection bypasses.
- Unverified CPU, memory, charging, or kernel tweaks.
- Claiming support for Android versions or devices that have not been tested.
