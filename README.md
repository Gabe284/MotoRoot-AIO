# MotoRoot AIO

An all-in-one Android root management and customization toolkit, initially developed for the **Motorola Moto G 2025 (XT2513-1)** running **Android 16**, stock Kansas firmware, and **KernelSU Next**.

The goal is to bring device-aware root capability reporting, module inventory and conflict analysis, reversible system customization, font and emoji management, diagnostics, and carefully bounded performance controls into one configurable interface.

> **Status: planning and foundation.** This repository now contains a minimal native Android app and a first-pass read-only audit screen. The build and device behavior are still under validation; there is no installer or device-modification feature. No feature should be considered implemented until its code and tests are present.

## Build APKs

GitHub Actions provides a simple build route:

1. Open [Actions → Android build](https://github.com/Gabe284/MotoRoot-AIO/actions/workflows/android-build.yml).
2. Select **Run workflow** on the development branch.
3. Choose **debug**, **release**, or **both** under **APK variant to build**.
4. Open the completed run and download the corresponding artifact.

Pushes to `main` or `feature/**` and pull requests targeting `main` run unit tests and build the debug APK automatically. A manually requested release build currently produces an **unsigned release APK**; it is for build validation, not a signed public release. Signing will be added only when a deliberate release-signing process is configured.

For a local build with JDK 17, Android SDK 36, and Gradle 8.13 installed:

```sh
gradle --no-daemon testDebugUnitTest assembleDebug
gradle --no-daemon assembleRelease
```

The debug variant uses application ID `dev.motoroot.aio.debug`; the release variant uses `dev.motoroot.aio`. Both are currently unminified.

## Initial goals

- Start with a read-only device and root capability audit.
- Use KernelSU Next as the primary root provider without replacing or bundling it.
- Keep root-provider integration behind a capability-based interface so other providers may be added later.
- Integrate selected, audited functionality from existing projects rather than combining entire codebases blindly.
- Build a font and emoji subsystem informed by aOpenFont, ZeroMount, and FancyMoji, subject to source-code and font-asset license review.
- Make changes previewable, logged, narrowly scoped, and reversible.
- Keep device-specific tuning gated by verified hardware and firmware capabilities.

## Reference device

- **Device:** Motorola Moto G 2025, XT2513-1
- **Platform:** Android 16, stock Kansas firmware
- **Root provider:** KernelSU Next
- **Initial compatibility target:** this exact device/firmware combination; broader compatibility is not implied.

See [Device Support](docs/DEVICE_SUPPORT.md), [Architecture](docs/ARCHITECTURE.md), and [Security Model](docs/SECURITY_MODEL.md).

## Development principles

1. Read-only discovery before modification.
2. Explicit user consent before applying changes.
3. Fail closed on unsupported or ambiguous targets.
4. No automatic uninstalling, disabling, or removing of other root modules.
5. Preserve stock system files and fallback behavior; use reversible overlays where supported.
6. Keep Google Play services/font suppression opt-in and disabled by default.
7. Do not bypass Android security protections or claim compatibility without testing.
8. Audit source-code and bundled asset licenses separately, and retain required attribution.

## Planned repository layout

```text
docs/                 Architecture, device support, security, porting plan
app/                  Native Android application (Kotlin; early skeleton)
core/                 Root-provider and device-capability abstractions (planned)
features/              Module manager, fonts, emoji, performance, diagnostics (planned)
tests/                 Unit, integration, and device validation (planned)
LICENSES/              Third-party notices and license records
```

Directories and features described as planned are not yet implemented.

## Upstream projects under evaluation

- [aOpenFont](https://github.com/Gabe284/aOpenFont) — font management work and architecture.
- [ZeroMount](https://github.com/Enginex0/zeromount) — existing emoji target discovery and mount logic; audit and adapt rather than import wholesale.
- [FancyMoji](https://github.com/newhertz/FancyMoji) — emoji-source selection and related UX ideas; review code and font asset licensing independently.
- [KernelSU Next](https://github.com/KernelSU-Next/KernelSU-Next) — primary root provider; initially integrate through supported interfaces instead of forking its root implementation.

See [Third-party notices](LICENSES/THIRD_PARTY_NOTICES.md). Mentioning a project here does not mean its code or assets have been copied into this repository.

## Contributions and safety

This project is not a substitute for backups or device-specific testing. Early development should use static analysis, unit tests, and an emulator or disposable test device before any Moto G deployment. Do not run unreviewed root scripts on a daily-use device.

## License

The project-wide license has not yet been selected. Until then, treat each contributed component according to its original license and do not assume that this repository grants permission to redistribute third-party fonts or other assets.
