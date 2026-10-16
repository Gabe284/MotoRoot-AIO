# MotoRoot AIO

An all-in-one Android root management and customization toolkit, initially developed for the **Motorola Moto G 2025 (XT2513-1)** running **Android 16**, stock Kansas firmware, and **KernelSU Next**.

The goal is to bring device-aware root capability reporting, module inventory and conflict analysis, reversible system customization, font and emoji management, diagnostics, and carefully bounded performance controls into one configurable interface.

> **Status: planning and foundation.** This repository does not yet contain a functional Android app, root service, installer, or device-modification scripts. No feature should be considered implemented until its code and tests are present.

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
core/                 Root-provider and device-capability abstractions (planned)
features/              Module manager, fonts, emoji, performance, diagnostics (planned)
ui/                    Unified configuration interface (planned)
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
