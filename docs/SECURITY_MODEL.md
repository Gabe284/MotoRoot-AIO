# Security Model

## Security objectives

- Preserve the user's existing boot, account-protection, and SELinux posture.
- Minimize privileged code and the number of operations it can perform.
- Make all mutations explicit, narrow, auditable, and recoverable where technically possible.
- Fail closed when the target, capability, source asset, or rollback method is uncertain.
- Avoid collecting or exposing unrelated personal data.

## Trust boundaries

1. **UI to policy layer:** UI values are untrusted input. Validate paths, options, sizes, identifiers, and operation parameters.
2. **Policy layer to root provider:** use supported interfaces and explicit capability checks. Do not treat a successful root shell as authorization for arbitrary changes.
3. **Feature to system:** confine each feature to an allowlisted set of targets and operations.
4. **Third-party assets:** validate file type and integrity, record hashes and licenses, and never execute downloaded assets.
5. **Diagnostics/export:** redact tokens, account identifiers, private paths, and unrelated log data.

## Mandatory rules

- Read-only audit before any write-capable feature is used.
- Show a plan of exact files, properties, modules, mounts, or values to be changed.
- Require explicit confirmation for each category of mutation.
- Do not uninstall, disable, or mark another module for removal automatically.
- Do not silently overwrite a file or modify an unrecognized target.
- Do not disable Google Play services/font-provider components or delete GMS font directories automatically.
- Keep GMS font suppression off by default.
- Do not bypass Android Advanced Account Protection, Play Integrity, package-install restrictions, SELinux, or boot verification.
- Do not weaken SELinux or the boot chain as a prerequisite for a feature.
- Avoid immutable flags, permission-000 directories, broad recursive replacement, or broad cache deletion as routine behavior.
- Never claim rollback is guaranteed unless restoration has been tested for that operation.

## Transaction requirements

Before mutation, store:
- The operation identifier and timestamp.
- The exact target list and pre-change state needed for recovery.
- Source and destination hashes where relevant.
- The selected method and its compatibility rationale.
- User confirmation and validation results.

During mutation:
- Apply one bounded operation at a time.
- Verify each step.
- Stop on unexpected state or target drift.
- Roll back already-applied steps where the rollback mechanism is validated.

After mutation:
- Verify the effective state rather than relying only on exit codes.
- Report failures and rollback outcomes accurately.
- Retain only the minimum diagnostic information required.

## Root command safety

- Do not build shell commands by concatenating unchecked UI strings.
- Prefer fixed commands with validated arguments and strict allowlists.
- Avoid shell evaluation and arbitrary user-provided scripts in the initial release.
- Separate inspection commands from mutation commands.
- Apply timeouts and bounded output handling where supported.
- Do not store root credentials or tokens in the app.

## Threats considered

- Malicious or malformed font files.
- Incorrect target discovery on vendor firmware.
- Conflicting root modules or mount providers.
- Partial application and interrupted rollback.
- Stale capability information after an OTA or module update.
- Accidental broad changes caused by path or glob mistakes.
- Diagnostic exports containing private data.

This document is a design requirement, not a claim that these controls are already implemented.
